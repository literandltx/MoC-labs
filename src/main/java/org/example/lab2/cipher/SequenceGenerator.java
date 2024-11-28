package org.example.lab2.cipher;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.example.lab2.Main.ALPHABET;
import static org.example.lab2.Main.BIGRAM_ALPHABET;

public class SequenceGenerator {
   private static final SecureRandom secureRandom = new SecureRandom(new byte[]{(byte) System.nanoTime(), (byte) System.currentTimeMillis()});

    public static List<String> generatedSequence(int L, int N, int exp) {
        final List<String> result = new ArrayList<>();

        if (exp == 1) {
            for (int i = 0; i < N; i++) {
                final StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j < L; j++) {
                    int index = secureRandom.nextInt(32);

                    stringBuilder.append(ALPHABET.charAt(index));
                }

                result.add(stringBuilder.toString());
            }
        }

        if (exp == 2) {
            for (int i = 0; i < N; i++) {
                final StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j++ < L; j += 2) {
                    int index = secureRandom.nextInt(1024);

                    stringBuilder.append(BIGRAM_ALPHABET.get(index));
                }

                result.add(stringBuilder.toString());
            }
        }

        return result;
    }

    public static List<String> generateCorrelationSequence(int L, int N, int exp) {
        final List<String> result = new ArrayList<>();

        if (exp == 1) {
            for (int i = 0; i < N; i++) {
                final StringBuilder stringBuilder = new StringBuilder();

                int s0 = secureRandom.nextInt(32);
                int s1 = secureRandom.nextInt(32);
                int s = (s0 + s1) % 32;

                stringBuilder.append(ALPHABET.charAt(s));

                for (int j = 1; j < L; j++) {
                    s0 = s1;
                    s1 = s;
                    s = (s0 + s1) % 32;

                    stringBuilder.append(ALPHABET.charAt(s));
                }

                result.add(stringBuilder.toString());
            }
        }

        if (exp == 2) {
            for (int i = 0; i < N; i++) {
                final StringBuilder stringBuilder = new StringBuilder();

                int s0 = secureRandom.nextInt(1024);
                int s1 = secureRandom.nextInt(1024);
                int s = (s0 + s1) % 1024;

                stringBuilder.append(BIGRAM_ALPHABET.get(s));

                for (int j = 1; j++ < L; j += 2) {
                    s0 = s1;
                    s1 = s;
                    s = (s0 + s1) % 1024;

                    stringBuilder.append(BIGRAM_ALPHABET.get(s));
                }

                result.add(stringBuilder.toString());
            }
        }

        return result;
    }
}
