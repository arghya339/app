package com.offlinew.practica.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JsonUtil {

    public static void concatJsonArrays(JSONArray target, JSONArray source) throws JSONException {
        for (int i = 0; i < source.length(); i++) {
            target.put(source.get(i));
        }
    }

    public static JSONArray sortJsonArrayByKey(JSONArray inputArray, String key, boolean isDecending) {
        try {
            // Convert JSONArray to ArrayList<JSONObject>
            ArrayList<JSONObject> jsonList = new ArrayList<>();
            for (int i = 0; i < inputArray.length(); i++) {
                jsonList.add(inputArray.getJSONObject(i));
            }

            // Sort by "score" in descending order
            Collections.sort(jsonList, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject a, JSONObject b) {
                    double scoreA = a.optDouble(key);
                    double scoreB = b.optDouble(key);
                    if(isDecending) {
                        return Double.compare(scoreB, scoreA); // Descending
                    }else {
                        return Double.compare(scoreA, scoreB); // Ascending
                    }
                }
            });

            // Convert back to JSONArray
            return new JSONArray(jsonList);

        } catch (Exception e) {
            e.printStackTrace();
            return inputArray; // Return original if exception occurs
        }
    }
}
