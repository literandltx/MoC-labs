package org.example.lab2.cipher;

import java.util.ArrayList;
import java.util.List;

import static org.example.lab2.Main.ALPHABET;
import static org.example.lab2.Main.BIGRAM_ALPHABET;

public class AffineCipher {
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
            int term;

            for (String x : text) {
                final StringBuilder temp = new StringBuilder();

                for (int j = 0; j + 1 < x.length(); j = j + 2) {
                    final int bigram = searchBigram(x.substring(j, j + 2));
                    term = (a * (bigram) + b) % (1024);

                    if (term < 0) {
                        term = ALPHABET.length() + term;
                    }

                    temp.append(BIGRAM_ALPHABET.get(term));
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

}
