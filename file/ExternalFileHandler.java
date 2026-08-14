package com.offlinew.android.file;

import android.os.Environment;

import java.io.File;

public class ExternalFileHandler {


    // returns
    public static File getPictureDirectory(){
        File dcimDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!dcimDirectory.exists()) {
            dcimDirectory.mkdirs();
        }
        File offlinePublicDirectory = new File(dcimDirectory,"OffLine");
        if (!offlinePublicDirectory.exists()) {
            offlinePublicDirectory.mkdirs();
        }

        return offlinePublicDirectory;
    }

//    public static File getOffLineDirectory(){
//        File exDirectory = Environment.getDataDirectory();
//        File offlinePublicDirectory = null;
//        if (exDirectory.exists()) {
//            offlinePublicDirectory = new File(exDirectory, "OffLine");
//            if (!offlinePublicDirectory.exists()) {
//                offlinePublicDirectory.mkdirs();
//            }
//        }
//        return offlinePublicDirectory;
//    }


}
