package org.example.lab2.util;

public class TextUtils {
    public static String processText(final String text) {
        return text
                .toLowerCase()
                .replaceAll("[^бвгґджзклмнпрстфхцчшщйаеєиіїоуюяь]", "")
                .replace("ґ", "г");
    }
}
