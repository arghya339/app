package com.offlinew.practica.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    /**
     * Saves a bitmap to a file.
     *
     * @param bitmap       The bitmap to save.
     * @param file         The file to save the bitmap into.
     * @param format       The compression format (e.g., JPEG, PNG).
     * @param quality      The quality of the compressed image (0-100).
     * @return             true if saving was successful, false otherwise.
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, File file, CompressFormat format, int quality) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(format, quality, out);
            out.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving bitmap to file", e);
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing FileOutputStream", e);
                }
            }
        }
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, String fullFilePath, Context context){
        File file = new File(fullFilePath);
        boolean isSuccess = saveBitmapToFile(bitmap, file, CompressFormat.PNG, 100);
        return isSuccess;
    }
}

