package com.offlinew.practica.utils;

public class ColorUtil {

    public static int getColorForHeight(float height, int alpha) {
        height = Math.max(0f, Math.min(1f, height)); // Clamp height to [0, 1]

        float[] stops = { 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f };
        int[] colors = {
                android.graphics.Color.argb(alpha,0, 0, 128),    // Deep water
                android.graphics.Color.argb(alpha,0, 0, 255),    // Shallow water
                android.graphics.Color.argb(alpha,0, 128, 0),    // Lowlands/grass
                android.graphics.Color.argb(alpha,139, 69, 19),  // Hills
                android.graphics.Color.argb(alpha,169, 169, 169),// Rocky
                android.graphics.Color.argb(alpha,255, 255, 255) // Snow
        };

        for (int i = 0; i < stops.length - 1; i++) {
            if (height >= stops[i] && height <= stops[i + 1]) {
                float ratio = (height - stops[i]) / (stops[i + 1] - stops[i]);
                int c0 = colors[i];
                int c1 = colors[i + 1];
                int r = (int) (android.graphics.Color.red(c0) + ratio * (android.graphics.Color.red(c1) - android.graphics.Color.red(c0)));
                int g = (int) (android.graphics.Color.green(c0) + ratio * (android.graphics.Color.green(c1) - android.graphics.Color.green(c0)));
                int b = (int) (android.graphics.Color.blue(c0) + ratio * (android.graphics.Color.blue(c1) - android.graphics.Color.blue(c0)));
                return android.graphics.Color.argb(alpha,r, g, b);
            }
        }

        return colors[colors.length - 1]; // fallback
    }


}
