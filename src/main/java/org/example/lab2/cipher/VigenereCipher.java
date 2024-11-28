package org.example.lab2.cipher;

import java.util.ArrayList;
import java.util.List;

public class VigenereCipher {
    private static final String ALPHABET = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя";

    public static ArrayList<String> vigenere(final List<String> text, final String key) {
        final ArrayList<String> result = new ArrayList<>();

        for (final String line : text) {
            final StringBuilder encryptedLine = new StringBuilder();
            int keyIndex = 0;

            for (int i = 0; i < line.length(); i++) {
                final char currentChar = Character.toLowerCase(line.charAt(i));

                if (Character.isLetter(currentChar)) {
                    final int baseIndex = ALPHABET.indexOf(currentChar);

                    if (baseIndex == -1) {
                        encryptedLine.append(currentChar);
                        continue;
                    }

                    final char keyChar = key.charAt(keyIndex % key.length());
                    final int shift = ALPHABET.indexOf(Character.toLowerCase(keyChar));

                    final char encryptedChar = ALPHABET.charAt((baseIndex + shift) % ALPHABET.length());

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
