package org.example.lab2.cipher;

import java.util.ArrayList;
import java.util.List;

public class Cipher {
    public static ArrayList<String> vigenere(final List<String> text, final String key) {
        final ArrayList<String> result = new ArrayList<>();

        for (String line : text) {
            StringBuilder encryptedLine = new StringBuilder();
            int keyIndex = 0;

            for (int i = 0; i < line.length(); i++) {
                char currentChar = line.charAt(i);

                if (Character.isLetter(currentChar)) {
                    boolean isUpperCase = Character.isUpperCase(currentChar);
                    char base = isUpperCase ? 'A' : 'a';

                    char keyChar = key.charAt(keyIndex % key.length());
                    int shift = Character.toUpperCase(keyChar) - 'A';

                    char encryptedChar = (char) ((currentChar - base + shift) % 26 + base);
                    encryptedLine.append(encryptedChar);

                    keyIndex++;
                } else {
                    encryptedLine.append(currentChar);
                }
            }

            result.add(encryptedLine.toString());
        }

        return result;
    }

}
