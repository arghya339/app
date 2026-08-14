package com.offlinew.android.file;

import com.offlinew.android.utils.UrlUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOperation {

    public static boolean copyFile(File sourceFile, File destFile) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // File copy successful
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., log or show an error message
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String saveThisVideo(String filePath, String desc){
        filePath = UrlUtil.cleanUrl(filePath);
        desc = UrlUtil.cleanUrl(desc);
        File src = new File(filePath);
        String fileName = "OffLine_";
        if(desc.length()>20) {
            fileName += desc.substring(0, 20)+ "_";
        }else{
            fileName += desc+ "_";
        }
        if(src.getName().length()>15) {
            fileName += src.getName().substring(src.getName().length() - 15);
        }else{
            fileName += src.getName();
        }
        File dst = new File(ExternalFileHandler.getPictureDirectory(),fileName);
        if(copyFile(src,dst)){
            return dst.getAbsolutePath();
        }
        return "";
    }

}
