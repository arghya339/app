# Android ImageLoader

A lightweight, dependency-free, production-ready image loader for Android Java.

## Features

-  Asynchronous image loading
-  Memory `LruCache`
-  Persistent disk cache
-  Downloads only when image is not cached
-  RecyclerView-safe `ImageView` reuse
-  Thread-safe
-  No external dependencies
-  Connection & read timeouts
-  Memory and disk cache management
-  HTTP/HTTPS support

## Setup

Add Internet permission:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Initialize once in your Application class:

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.init(getCacheDir());
    }
}
```

Register the application:

```xml
<application
    android:name=".App"
    ... >
```

### Usage
```java
ImageLoader.getInstance().load(
        imageUrl,
        imageView
);
```

With placeholder and error image:

```java
ImageLoader.getInstance().load(
        imageUrl,
        imageView,
        R.drawable.placeholder,
        R.drawable.error
);
```

### RecyclerView
```java
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String imageUrl = items.get(position).getImageUrl();

    ImageLoader.getInstance().load(
            imageUrl,
            holder.imageView,
            R.drawable.placeholder,
            R.drawable.error
    );
}
```

The loader automatically handles ImageView reuse, making it safe for fast-scrolling RecyclerView and grid layouts.

### Cache Management

Clear memory cache:
```java
ImageLoader.getInstance().clearMemoryCache();
```

Clear disk cache:

```java
ImageLoader.getInstance().clearDiskCache();
```

Clear both:
```java
ImageLoader.getInstance().clearAll();
```

### How It Works
```
                ┌─────────────────┐
                │    Image URL    │
                └────────┬────────┘
                         │
                ┌────────▼────────┐
                │   Memory Cache  │
                └────────┬────────┘
                    Hit  │  Miss
                ┌────────▼────────┐
                │    Disk Cache   │
                └────────┬────────┘
                    Hit  │  Miss
                ┌────────▼────────┐
                │     Network     │
                └────────┬────────┘
                         │
              ┌──────────▼───────────┐
              │ Memory + Disk Cache  │
              └──────────┬───────────┘
                         │
                   ┌─────▼─────┐
                   │ ImageView │
                   └───────────┘
```
### Use Cases

Perfect for:

- RecyclerView
- Grid layouts
- Dynamic card layouts
- Book covers
- Thumbnails
- User avatars
- Remote images
- Offline-friendly image caching

### Requirements
- Android
- Java
- No third-party libraries required

### License

MIT License
