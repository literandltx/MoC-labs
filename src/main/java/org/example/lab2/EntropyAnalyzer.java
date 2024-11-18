package org.example.lab2;

import java.util.Map;

public class EntropyAnalyzer {
    public static double entropyNGram(final Map<?, Double> map, final int N) {
        double entropy = 0;
        final double[] freq = map.values().stream()
                .mapToDouble(Double::doubleValue)
                .toArray();

        for (double i : freq) {
            entropy -= i * Math.log(i)/Math.log(2);
        }

        return entropy / N;
    }
}
