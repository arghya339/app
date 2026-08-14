package com.offlinew.android.file;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.offlinew.android.Log.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IFH {


    public static void moveFileFullPath(String sourceFullPath, String destinationFullPath){
        Log.d("IFH","source:"+sourceFullPath+"\ndestination:"+destinationFullPath);
        File sourceFile = new File(sourceFullPath);
        File destinationFile = new File(destinationFullPath);

        try {
            boolean success = sourceFile.renameTo(destinationFile);
            if (success) {
                Log.d("IFH","File moved successfully.");
            } else {
                Log.d("IFH","Failed to move file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void moveFile(String sourceFolderPath, String destinationFolderPath, String fileName) {
        File sourceFile = new File(sourceFolderPath, fileName);
        File destinationFolder = new File(destinationFolderPath);
        if (!sourceFile.exists()) {
            Log.d("IFH","Source file does not exist.");
            return;
        }
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }
        File destinationFile = new File(destinationFolder, fileName);

        try {
            boolean success = sourceFile.renameTo(destinationFile);
            if (success) {
                Log.d("IFH","File moved successfully.");
            } else {
                Log.d("IFH","Failed to move file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getFileNameFromUri(Context context, Uri uri) {
        String result = null;

        // Check if the URI is a content URI
        if (uri.getScheme().equals("content")) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
        }

        // If the URI is a file URI or if result is still null
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return result;
    }


    //10-08-24 51
    public static boolean saveFileFromUriToStorage(Context context, Uri fileUri, String destination, String fileName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = context.getContentResolver().openInputStream(fileUri);
            if (inputStream == null) {
                Log.e("FileUtils", "Failed to open input stream for the URI.");
                return false;
            }

            if(fileName.equals("")){
                fileName = getFileNameFromUri(context,fileUri);
            }

            File outFolder = new File(destination);
            File outputFile = new File(destination, fileName);

            outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[512*1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            return true; // Successfully saved the video to internal storage.
        } catch (IOException e) {
            Log.e("FileUtils", "Error saving file to internal storage: " + e.getMessage());
            return false; // Error occurred while saving the video.
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                Log.e("FileUtils", "Error closing streams: " + e.getMessage());
            }
        }
    }

    //10-08-24 51
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return ""; // Return null if there's no extension
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static String filenameToFileType(String filename){
        String ext = getFileExtension(filename);
        return FileTypeUtil.extensionToType(ext);
    }
    public static String extensionToFileType(String ext){
        return FileTypeUtil.extensionToType(ext);
    }

}
