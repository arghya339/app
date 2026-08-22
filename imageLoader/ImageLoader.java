package com.offlinew.practica.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.offlinew.practica.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ImageLoader {

    private static volatile ImageLoader instance;
    private final File diskCacheDir;
    private final LruCache<String, Bitmap> memoryCache;
    private final ExecutorService executor;
    private final Handler mainHandler;

    /**
     * Tracks currently running requests.
     *
     * One URL can have multiple ImageViews waiting for the same result.
     */
    private final Map<String, DownloadTask> runningTasks = new ConcurrentHashMap<>();
    private static final int CONNECT_TIMEOUT = 15_000;
    private static final int READ_TIMEOUT = 20_000;
    private static final int DISK_BUFFER_SIZE = 8 * 1024;
    private ImageLoader(@NonNull File cacheDirectory) {

        diskCacheDir = new File(cacheDirectory, "images");
        if (!diskCacheDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            diskCacheDir.mkdirs();
        }

        final int maxMemoryKb = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSizeKb = maxMemoryKb / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSizeKb) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

        /*
         * A bounded pool prevents creating an unlimited number of threads
         * when many images are requested simultaneously.
         */
        int threadCount = Math.max(2, Math.min(4, Runtime.getRuntime().availableProcessors()));
        executor = Executors.newFixedThreadPool(threadCount);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Initialize the singleton.
     *
     * Call once from Application.onCreate().
     */
    public static void init(@NonNull File cacheDirectory) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader(cacheDirectory);
                }
            }
        }
    }

    /**
     * Returns initialized singleton.
     *
     * If init() was not explicitly called, this throws a clear error
     * instead of silently creating an Application-dependent instance.
     */
    @NonNull
    public static ImageLoader getInstance() {

        ImageLoader loader = instance;

        if (loader == null) {
            throw new IllegalStateException(
                    "ImageLoader is not initialized. " +
                    "Call ImageLoader.init(context.getCacheDir()) " +
                    "from Application.onCreate()."
            );
        }

        return loader;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Load image into ImageView.
     */
    public void load(@Nullable String url, @NonNull ImageView imageView) {
        load(url, imageView, 0, 0);
    }

    /**
     * Load image with placeholder and error drawable.
     */
    public void load(@Nullable String url, @NonNull ImageView imageView, @DrawableRes int placeholderRes, @DrawableRes int errorRes) {
        /*
         * Cancel the previous request associated with this ImageView.
         * This is extremely important for RecyclerView.
         */
        cancel(imageView);

        if (placeholderRes != 0) {
            imageView.setImageResource(placeholderRes);
        } else {
            imageView.setImageDrawable(null);
        }

        if (url == null || url.trim().isEmpty()) {
            if (errorRes != 0) {
                imageView.setImageResource(errorRes);
            }
            return;
        }

        final String cleanUrl = url.trim();

        /*
         * Token prevents an old asynchronous request from updating
         * an ImageView after it has been reused.
         */
        final RequestToken token = new RequestToken(cleanUrl);
        imageView.setTag(R.id.image_loader_request_token, token);
        Bitmap memoryBitmap = memoryCache.get(cleanUrl);
        if (memoryBitmap != null && !memoryBitmap.isRecycled()) {
            imageView.setImageBitmap(memoryBitmap);
            imageView.setTag(R.id.image_loader_request_token, null);
            return;
        }

        /*
         * First check disk cache asynchronously.
         */
        executor.execute(() -> {
            Bitmap bitmap = loadFromDisk(cleanUrl);
            if (bitmap != null) {
                memoryCache.put(cleanUrl, bitmap);
                postResult(cleanUrl, imageView, token, bitmap, errorRes);
                return;
            }

            /*
             * Disk cache miss.
             */
            DownloadTask task = runningTasks.get(cleanUrl);
            if (task == null) {
                DownloadTask newTask = new DownloadTask(cleanUrl);
                DownloadTask existing = runningTasks.putIfAbsent(cleanUrl, newTask);
                task = existing != null ? existing : newTask;
//                if (existing == null) {
//                    task.future = executor.submit(() -> {
//                        Bitmap downloaded = null;
//                        try {
//                            downloaded = download(cleanUrl);
//                            if (downloaded != null) {
//                                memoryCache.put(cleanUrl,downloaded);
//                                saveToDisk(cleanUrl,downloaded);
//                            }
//                        } finally {
//                            runningTasks.remove(cleanUrl,task);
//                        }
//
//                        if (downloaded != null) {
//                            postResult(cleanUrl, imageView, token, downloaded, errorRes);
//                            /*
//                             * Other ImageViews waiting for the same URL
//                             * will receive the cached bitmap through their
//                             * own disk/memory path.
//                             */
//                        } else {
//                            postError(imageView,token,errorRes);
//                        }
//                    });
//
//                } else {
//
//                    /*
//                     * Existing request will eventually populate the cache.
//                     * Polling is avoided; instead retry once on the main
//                     * thread after completion isn't necessary because
//                     * callers commonly arrive before completion.
//                     *
//                     * Attach a lightweight waiter.
//                     */
//                    task.addWaiter(imageView,token,errorRes);
//                }

                if (existing == null) {
                    DownloadTask finalTask = task;
                    task.future = executor.submit(() -> {
                        Bitmap downloaded = null;
                        try {
                            downloaded = download(cleanUrl);
                            if (downloaded != null) {
                                memoryCache.put(cleanUrl, downloaded);
                                saveToDisk(cleanUrl, downloaded);
                            }
                        } finally {
                            runningTasks.remove(cleanUrl, finalTask);
                        }

                        final Bitmap result = downloaded;

                        mainHandler.post(() -> {

                            /*
                             * First ImageView.
                             */
                            updateImageView(imageView, token, result, errorRes);
                            /*
                             * All ImageViews waiting for the same URL.
                             */
                            for (Waiter waiter : finalTask.getWaiters()) {
                                updateImageView(waiter.imageView, waiter.token, result, waiter.errorRes);
                            }
                        });
                    });
                } else {
                    task.addWaiter(imageView, token, errorRes);
                }
            } else {
                task.addWaiter(imageView, token, errorRes);
            }
        });
    }


    private void updateImageView(@NonNull ImageView imageView, @NonNull RequestToken token, @Nullable Bitmap bitmap, @DrawableRes int errorRes) {
        Object tag = imageView.getTag(R.id.image_loader_request_token);
        /*
         * ImageView was reused or another load() was called.
         */
        if (tag != token) {
            return;
        }

        if (bitmap != null && !bitmap.isRecycled()) {
            imageView.setImageBitmap(bitmap);
        } else if (errorRes != 0) {
            imageView.setImageResource(errorRes);
        }

        imageView.setTag(R.id.image_loader_request_token, null);
    }

    /**
     * Cancel request associated with an ImageView.
     */
    public void cancel(@NonNull ImageView imageView) {
        imageView.setTag(R.id.image_loader_request_token, null);
    }

    /**
     * Clear memory cache.
     */
    public void clearMemoryCache() {
        memoryCache.evictAll();
    }

    /**
     * Clear disk cache asynchronously.
     */
    public void clearDiskCache() {
        executor.execute(() -> {
            File[] files = diskCacheDir.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isFile()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
            }
        });
    }

    /**
     * Clear both caches.
     */
    public void clearAll() {
        clearMemoryCache();
        clearDiskCache();
    }

    // -------------------------------------------------------------------------
    // Disk cache
    // -------------------------------------------------------------------------

    @Nullable
    private Bitmap loadFromDisk(@NonNull String url) {
        File file = getCacheFile(url);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream input = new FileInputStream(file)) {
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            if (bitmap == null) {
                // Corrupted cache entry.
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private void saveToDisk(@NonNull String url, @NonNull Bitmap bitmap) {
        File target = getCacheFile(url);
        File temp = new File(diskCacheDir, target.getName() + ".tmp");
        try (FileOutputStream output = new FileOutputStream(temp);
             BufferedOutputStream buffered = new BufferedOutputStream(output, DISK_BUFFER_SIZE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffered);
            buffered.flush();

            /*
             * Atomic-ish replacement:
             * write completely to .tmp first, then rename.
             */
            if (target.exists()) {
                //noinspection ResultOfMethodCallIgnored
                target.delete();
            }

            //noinspection ResultOfMethodCallIgnored
            temp.renameTo(target);

        } catch (Exception ignored) {
            //noinspection ResultOfMethodCallIgnored
            temp.delete();
        }
    }

    @NonNull
    private File getCacheFile(@NonNull String url) {
        return new File(diskCacheDir, sha256(url) + ".png");
    }

    // -------------------------------------------------------------------------
    // Network
    // -------------------------------------------------------------------------

    @Nullable
    private Bitmap download(@NonNull String url) {
        HttpURLConnection connection = null;
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                return null;
            }

            connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setUseCaches(true);
            connection.setInstanceFollowRedirects(true);

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Accept", "image/*");

            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            try (InputStream input = new BufferedInputStream(connection.getInputStream())) {
                return BitmapFactory.decodeStream(input);
            }

        } catch (IOException | URISyntaxException | SecurityException e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // -------------------------------------------------------------------------
    // UI result handling
    // -------------------------------------------------------------------------

    private void postResult(@NonNull String url, @NonNull ImageView imageView,
            @NonNull RequestToken token, @NonNull Bitmap bitmap, @DrawableRes int errorRes) {

        mainHandler.post(() -> {
            Object tag = imageView.getTag(R.id.image_loader_request_token);
            if (tag != token) {
                return;
            }

            if (!url.equals(token.url)) {
                return;
            }

            imageView.setImageBitmap(bitmap);
            imageView.setTag(R.id.image_loader_request_token, null);
        });
    }

    private void postError(@NonNull ImageView imageView, @NonNull RequestToken token, @DrawableRes int errorRes) {

        mainHandler.post(() -> {
            Object tag = imageView.getTag(R.id.image_loader_request_token);

            if (tag != token) {
                return;
            }

            if (errorRes != 0) {
                imageView.setImageResource(errorRes);
            }

            imageView.setTag(R.id.image_loader_request_token, null
            );
        });
    }

    // -------------------------------------------------------------------------
    // Utilities
    // -------------------------------------------------------------------------

    @NonNull
    private static String sha256(@NonNull String value) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                builder.append(String.format("%02x", b & 0xff));
            }

            return builder.toString();

        } catch (Exception e) {

            /*
             * SHA-256 is required by every Android runtime,
             * but keep a deterministic fallback.
             */
            return Integer.toHexString(value.hashCode());
        }
    }

    // -------------------------------------------------------------------------
    // Internal request classes
    // -------------------------------------------------------------------------

    private static final class RequestToken {
        final String url;
        RequestToken(@NonNull String url) {
            this.url = url;
        }
    }

    private static final class Waiter {
        final ImageView imageView;
        final RequestToken token;
        final int errorRes;
        Waiter(@NonNull ImageView imageView, @NonNull RequestToken token, int errorRes) {
            this.imageView = imageView;
            this.token = token;
            this.errorRes = errorRes;
        }
    }

    private static final class DownloadTask {
        final String url;
        volatile Future<?> future;
        private final java.util.List<Waiter> waiters = new java.util.concurrent.CopyOnWriteArrayList<>();
        DownloadTask(@NonNull String url) {
            this.url = url;
        }
        void addWaiter(@NonNull ImageView imageView, @NonNull RequestToken token, int errorRes) {
            waiters.add(new Waiter(imageView, token, errorRes));
        }

        java.util.List<Waiter> getWaiters() {
            return waiters;
        }
    }
}