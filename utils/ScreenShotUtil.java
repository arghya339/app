package com.offlinew.practica.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.offlinew.practica.Log.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenShotUtil {

    public static String TAG = "ScreenShotUtil";

    public static Bitmap getBitmapFromView(View view) {
        // Measure and layout the view
        view.measure(
                View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY)
        );
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());

        // Create a bitmap with the same size as the view
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        // Draw the view into the canvas
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    public static boolean saveBitmapToFile(Bitmap bitmap, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {}
            }
        }
    }


    public static void captureAndShareScreenshot(Context context, View view, String textToShare) {
        // Step 1: Capture bitmap
        Bitmap bitmap = getBitmapFromView(view);

        if (bitmap == null) {
            //Toast.makeText(this, "Failed to capture screenshot", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Failed to capture screenshot" );
            return;
        }

        // Step 2: Save to cache directory
        File cachePath = new File(context.getCacheDir(), "images");
        cachePath.mkdirs(); // create folder if not exists
        File file = new File(cachePath, "screenshot_"+RandomNumberUtil.randomNumberBetween(200000,938648)+".png");

        boolean success = saveBitmapToFile(bitmap, file);
        if (!success) {
            //Toast.makeText(this, "Failed to save screenshot", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Failed to save screenshot");
            return;
        }

        // Step 3: Get URI using FileProvider
        Uri uri = FileProvider.getUriForFile(context,context.getPackageName() + ".provider", file);

        // Step 4: Share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot via"));
    }



}
