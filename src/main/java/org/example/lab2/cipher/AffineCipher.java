package org.example.lab2.cipher;

import java.util.ArrayList;
import java.util.List;

public class AffineCipher {
    private static final String ALPHABET = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя";
    private static final List<String> BIGRAM_ALPHABET = createBigramAlphabet();

    public static List<String> affine(
            final List<String> text,
            final int a,
            final int b,
            final int exp
    ) {
        final ArrayList<String> result = new ArrayList<>();

        if (exp == 1) {
            int term;

            for (String x : text) {
                final StringBuilder temp = new StringBuilder();

                for (int j = 0; j < x.length(); j++) {
                    int letter = searchLetter(x.charAt(j));

                    term = (a * (letter) + b) % 32;
                    temp.append(ALPHABET.charAt(term));
                }

                result.add(temp.toString());
            }
        }

        if (exp == 2) {
            final List<String> allBi = createBigramAlphabet();
            int term;

            for (String x : text) {
                final StringBuilder temp = new StringBuilder();

                for (int j = 0; j + 1 < x.length(); j = j + 2) {
                    final int bigram = searchBigram(x.substring(j, j + 2));
                    term = (a * (bigram) + b) % (1024);

                    if (term < 0) {
                        term = ALPHABET.length() + term;
                    }

                    temp.append(allBi.get(term));
                }

                result.add(temp.toString());
            }
        }

        return result;
    }

    private static int searchLetter(final char let) {
        return ALPHABET.indexOf(let);
    }

    private static int searchBigram(final String bigram) {
        return BIGRAM_ALPHABET.indexOf(bigram);
    }

    private static List<String> createBigramAlphabet() {
        final List<String> allBi = new ArrayList<>(ALPHABET.length() * ALPHABET.length());

        for (int i = 0; i < ALPHABET.length(); i++) {
            for (int j = 0; j < ALPHABET.length(); j++) {
                allBi.add(String.valueOf(ALPHABET.charAt(i)) + ALPHABET.charAt(j));
            }
        }

        return allBi;
    }

}
