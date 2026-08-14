package com.offlinew.practica.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
 *  Zip / UnZip folder
 * # usage
 *      ZipUtil.zipFiles(Environment.getExternalStorageDirectory()+"/TestFolder/ToZip",Environment.getExternalStorageDirectory()+"/TestFolder/ToZip.zip");
 *      ZipUtil.unzip(Environment.getExternalStorageDirectory()+"/TestFolder/ToZip.zip",Environment.getExternalStorageDirectory()+"/TestFolder/Unzip");
 */
public class ZipUtil {

    /*
     * Zip a folder, maintaining directory structure
     * including the current folder
     */
    public static boolean zipFiles(String sourceFolderFullPath, String zipFileFullPath) {
       try {
           File sourceFolder = new File(sourceFolderFullPath);
           FileOutputStream fos = new FileOutputStream(zipFileFullPath);
           ZipOutputStream zos = new ZipOutputStream(fos);

           addFolderToZip(sourceFolder, sourceFolder, zos);

           zos.close();
           fos.close();
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }

    private static void addFolderToZip(File rootFolder, File sourceFolder, ZipOutputStream zos) {
        try {
            File[] files = sourceFolder.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    addFolderToZip(rootFolder, file, zos);
                } else {
                    String relativePath = rootFolder.getName()+"/"+file.getAbsolutePath().substring(rootFolder.getAbsolutePath().length() + 1);

                    ZipEntry zipEntry = new ZipEntry(relativePath);
                    zos.putNextEntry(zipEntry);

                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, bytesRead);
                    }

                    fis.close();
                    zos.closeEntry();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * UnZip a Zip file, maintaining directory structure
     * @param zipFilePath
     * @param destFolderPath
     */

    public static void unzip(String zipFilePath, String destFolderPath) {
        try {
            File destFolder = new File(destFolderPath);
            if (!destFolder.exists()) {
                destFolder.mkdirs();
            }

            FileInputStream fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);

            ZipEntry zipEntry;
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((zipEntry = zis.getNextEntry()) != null) {
                String entryName = zipEntry.getName();
                String entryPath = destFolderPath + File.separator + entryName;

                if (zipEntry.isDirectory()) {
                    File dir = new File(entryPath);
                    dir.mkdirs();
                } else {
                    File file = new File(entryPath);
                    file.getParentFile().mkdirs();

                    FileOutputStream fos = new FileOutputStream(file);
                    while ((bytesRead = zis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    fos.close();
                }

                zis.closeEntry();
            }

            zis.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
