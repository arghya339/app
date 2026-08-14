package com.offlinew.practica.utils;

import java.util.ArrayList;
import java.util.Collections;

public class StringUtils {
    public static String getCommonPrefix(ArrayList<String> list) {
        // Edge case: If the list is empty or null, there is no prefix
        if (list == null || list.isEmpty()) {
            return "";
        }

        // 1. Sort the arraylist alphabetically
        Collections.sort(list);

        // 2. Grab the first and last strings
        String first = list.get(0);
        String last = list.get(list.size() - 1);

        // 3. Find the common characters between first and last
        int index = 0;
        while (index < first.length() && index < last.length()) {
            if (first.charAt(index) == last.charAt(index)) {
                index++;
            } else {
                break; // Stop as soon as a character doesn't match
            }
        }

        // 4. Return the matching prefix substring
        return first.substring(0, index);
    }
}
