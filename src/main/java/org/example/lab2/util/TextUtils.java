package org.example.lab2.util;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static String processText(final String text) {
        return text
                .toLowerCase()
                .replaceAll("[^бвгґджзклмнпрстфхцчшщйаеєиіїоуюяь]", "")
                .replace("ґ", "г");
    }

    public static List<String> getSequentialSubstrings(final String inputText, int X, int L) {
        final List<String> substrings = new ArrayList<>();

        if (inputText.length() < L * X) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < X; i++) {
            int startIndex = i * L;

            substrings.add(inputText.substring(startIndex, startIndex + L));
        }

        return substrings;
    }
}
