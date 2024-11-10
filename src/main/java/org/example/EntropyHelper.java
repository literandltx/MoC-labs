package org.example;

import java.util.Map;

public class EntropyHelper {
    public static double entropyNGram(Map<?, Double> map, int N) {
        double entropy = 0;
        double[] freq = map.values().stream().mapToDouble(Double::doubleValue).toArray();

        for (double i : freq) {
            entropy -= i * Math.log(i)/Math.log(2);
        }

        return entropy / N;
    }
}
