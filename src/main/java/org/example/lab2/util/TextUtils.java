package org.example.lab2.util;

import java.util.ArrayList;
import java.util.List;

import static org.example.lab2.Main.ALPHABET;

public class TextUtils {
    public static String processText(final String text) {
        return text
                .toLowerCase()
                .replaceAll("[^бвгґджзклмнпрстфхцчшщйаеєиіїоуюяь]", "")
                .replace("ґ", "г");
    }

    public static List<String> getSequentialSubstrings(final String inputText, int num, int len) {
        final List<String> substrings = new ArrayList<>();

        if (inputText.length() < len * num) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < num; i++) {
            int startIndex = i * len;

            substrings.add(inputText.substring(startIndex, startIndex + len));
        }

        return substrings;
    }

    public static List<String> createBigramAlphabet() {
        final List<String> allBi = new ArrayList<>(ALPHABET.length() * ALPHABET.length());

        for (int i = 0; i < ALPHABET.length(); i++) {
            for (int j = 0; j < ALPHABET.length(); j++) {
                allBi.add(String.valueOf(ALPHABET.charAt(i)) + ALPHABET.charAt(j));
            }
        }

        return allBi;
    }

}
