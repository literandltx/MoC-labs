package org.example.lab2.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.lab2.Main.ALPHABET;

public class LetterFrequencyAnalyzer {
    public static Map<Character, Integer> getLetterFrequencies(final String text) {
        final Map<Character, Integer> frequencies = new HashMap<>();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toLowerCase(c);

                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }
        }

        return frequencies;
    }

    public static Map<Character, Double> getNormalizeLetterFrequencies(final Map<Character, Integer> letterFrequencies) {
        final Map<Character, Double> normalizedFrequencies = new HashMap<>();

        final int totalLetters = letterFrequencies.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        for (Map.Entry<Character, Integer> entry : letterFrequencies.entrySet()) {
            final char letter = entry.getKey();
            final int count = entry.getValue();
            final double normalizedFrequency = (double) count / totalLetters;

            normalizedFrequencies.put(letter, normalizedFrequency);
        }

        return normalizedFrequencies;
    }

    public static Map<Character, Integer> getPopularLetterFrequencies(final String text, final int limit) {
        return getLetterFrequencies(text).entrySet().stream()
                .filter(entry -> entry.getValue() > limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Character, Integer> getNonePopularLetterFrequencies(final String text, final int limit) {
        return getLetterFrequencies(text).entrySet().stream()
                .filter(entry -> entry.getValue() < limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static double findLetterIndex(final String text, final Map<Character, Integer> letterFrequencies) {
        final int length = text.length();
        double index = 0;

        if (length < 2) {
            return 0;
        }

        for (final int frequency : letterFrequencies.values()) {
            index += frequency * (frequency - 1);
        }

        return index / (length * (length - 1));
    }

}