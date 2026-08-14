package com.offlinew.practica.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.offlinew.practica.R;

public class ClipboardUtil {

    /**
     *
     * @param context
     * @param label human readale one
     * @param text
     */
    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, context.getString(R.string.Copied_to_clipboard), Toast.LENGTH_SHORT).show();
    }
}
