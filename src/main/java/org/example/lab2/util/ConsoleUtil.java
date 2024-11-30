package org.example.lab2.util;

import java.text.DecimalFormat;
import java.util.Map;

public class ConsoleUtil {
    private static final DecimalFormat df = new DecimalFormat("#0");

    public static void displayFrequencies(final Map<?, ?> frequencies) {
        int count = 1;
        for (var entry : frequencies.entrySet()) {
            System.out.printf("%-3s. %s: %s%n", df.format(count++), entry.getKey(), entry.getValue());
        }
    }
}
