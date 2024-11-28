package org.example.lab2.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BigramFrequencyAnalyzer {
    public static Map<String, Integer> getBigramFrequencies(final String text) {
        final Map<String, Integer> bigramFrequencies = new HashMap<>();

        for (int i = 0; i < text.length() - 1; i++) {
            final String bigram = text.substring(i, i + 2);

            bigramFrequencies.put(bigram, bigramFrequencies.getOrDefault(bigram, 0) + 1);
        }

        return bigramFrequencies;
    }

    public static Map<String, Double> getNormalizeBigramFrequencies(final Map<String, Integer> bigramFrequencies) {
        final Map<String, Double> normalizedFrequencies = new HashMap<>();

        final int totalBigrams = bigramFrequencies.values().stream().mapToInt(Integer::intValue).sum();

        for (final Map.Entry<String, Integer> entry : bigramFrequencies.entrySet()) {
            final String bigram = entry.getKey();
            final int count = entry.getValue();
            final double normalizedFrequency = (double) count / totalBigrams;

            normalizedFrequencies.put(bigram, normalizedFrequency);
        }

        return normalizedFrequencies;
    }

    public static Map<String, Integer> getPopularBigramFrequencies(final String text, final int limit) {
        return getBigramFrequencies(text).entrySet().stream()
                .filter(entry -> entry.getValue() > limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, Integer> getNonePopularBigramFrequencies(final String text, final int limit) {
        return getBigramFrequencies(text).entrySet().stream()
                .filter(entry -> entry.getValue() < limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}