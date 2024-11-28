package org.example.lab2.analyzer;

import java.util.HashMap;
import java.util.Map;

public class BigramFrequencyAnalyzer {
    public static Map<String, Integer> getBigramFrequencies(final String text) {
        final Map<String, Integer> bigramFrequencies = new HashMap<>();

        for (int i = 0; i < text.length() - 1; i++) {
            String bigram = text.substring(i, i + 2);
            bigramFrequencies.put(bigram, bigramFrequencies.getOrDefault(bigram, 0) + 1);
        }

        return bigramFrequencies;
    }

    public static Map<String, Double> getNormalizeBigramFrequencies(final Map<String, Integer> bigramFrequencies) {
        final Map<String, Double> normalizedFrequencies = new HashMap<>();

        int totalBigrams = bigramFrequencies.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : bigramFrequencies.entrySet()) {
            String bigram = entry.getKey();
            int count = entry.getValue();
            double normalizedFrequency = (double) count / totalBigrams;
            normalizedFrequencies.put(bigram, normalizedFrequency);
        }

        return normalizedFrequencies;
    }
}