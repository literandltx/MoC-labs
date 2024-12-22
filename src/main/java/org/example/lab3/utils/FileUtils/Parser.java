package org.example.lab3.utils.FileUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<BigInteger> parseCFromSE(final List<String> rawSE) {
        final List<BigInteger> result = new ArrayList<>();

        for (final String s : rawSE) {
            if (s.charAt(0) == 'C') {
                result.add(new BigInteger(s.substring(7), 16));
            }
        }

        return result;
    }

    public static List<BigInteger> parseNFromSE(final List<String> rawSE) {
        final List<BigInteger> result = new ArrayList<>();

        for (final String s : rawSE) {
            if (s.charAt(0) == 'N') {
                result.add(new BigInteger(s.substring(7), 16));
            }
        }

        return result;
    }

    public static BigInteger parseCFromMitM(final List<String> rawSE) {
        for (final String s : rawSE) {
            if (s.charAt(0) == 'C') {
                return new BigInteger(s.substring(6), 16);
            }
        }

        throw new IllegalArgumentException("C not found");
    }

    public static BigInteger parseNFromMitM(final List<String> rawSE) {
        for (final String s : rawSE) {
            if (s.charAt(0) == 'N') {
                return new BigInteger(s.substring(6), 16);
            }
        }
        throw new IllegalArgumentException("N not found");
    }
}
