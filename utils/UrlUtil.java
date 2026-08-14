package com.offlinew.practica.utils;

import java.util.regex.Pattern;

public class UrlUtil {

    private static final Pattern UNWANTED_CHARACTERS = Pattern.compile("[^a-zA-Z0-9-._/]+");

    public static String cleanUrl(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }
        // Replace unwanted characters with an empty string
        return UNWANTED_CHARACTERS.matcher(url).replaceAll("");
    }

}
