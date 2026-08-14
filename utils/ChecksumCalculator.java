package com.offlinew.practica.utils;

import com.offlinew.practica.crypto.CryptoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumCalculator {
    /*
    //# usage
    File fileToCheck = new File("/path/to/your/file");
    String algorithm = "SHA-256";// SHA-1 MD5 // Choose the desired algorithm
    try {
        String checksum = ChecksumCalculator.calculateChecksum(fileToCheck, algorithm);
        // Use the checksum as needed
    } catch (NoSuchAlgorithmException | IOException e) {
        e.printStackTrace();
    }

     */

    public static byte[] calculateChecksumFile(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[8192];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        byte[] hashBytes = digest.digest();
        return hashBytes;

    }

    public static String calculateChecksumFileBase64x(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
        byte[] hashBytes = calculateChecksumFile(file,algorithm);
        return CryptoUtils.byteArrayToBase64x(hashBytes);
    }

//    public static String calculateChecksumFileHex(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
//        byte[] hashBytes = calculateChecksumFile(file,algorithm);
//
//        StringBuilder hexStringBuilder = new StringBuilder();
//        for (byte hashByte : hashBytes) {
//            hexStringBuilder.append(String.format("%02x", hashByte));
//        }
//
//        return hexStringBuilder.toString();
//    }

    public static byte[] calculateChecksumString(String data, String algorithm) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(algorithm);
        digest.update(data.getBytes());
        byte[] hashBytes = digest.digest();

        return hashBytes;



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
            //throw new RuntimeException(e);
        }
    }

    public static String calculateChecksumStringHex(String data, String algorithm) {
        byte[] hashBytes = calculateChecksumString( data, algorithm);
        StringBuilder result = new StringBuilder();
        for (byte hashByte : hashBytes) {
            result.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }

        return result.toString();
    }

    public static String calculateChecksumStringBase64x(String data, String algorithm) {
        byte[] hashBytes = calculateChecksumString( data, algorithm);
        return CryptoUtils.byteArrayToBase64x(hashBytes);
    }

}
