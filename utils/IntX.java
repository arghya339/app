package com.offlinew.practica.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntX {

    public static int extractNumber(String filename) {
        // Use regex to match digits between non-digit characters
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return -1; // or throw exception if needed
    }


}
