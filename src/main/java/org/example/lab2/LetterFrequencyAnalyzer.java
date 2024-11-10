package org.example.lab2;

import java.util.HashMap;
import java.util.Map;

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

        int totalLetters = letterFrequencies.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<Character, Integer> entry : letterFrequencies.entrySet()) {
            char letter = entry.getKey();
            int count = entry.getValue();
            double normalizedFrequency = (double) count / totalLetters;
            normalizedFrequencies.put(letter, normalizedFrequency);
        }

        return normalizedFrequencies;
    }
}