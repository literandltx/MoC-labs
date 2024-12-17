package org.example.lab3.utils.FileUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ParserSE {
    public static List<BigInteger> getC(final List<String> rawSE) {
        final List<BigInteger> result = new ArrayList<>();

        for (final String s : rawSE) {
            if (s.charAt(0) == 'C') {
                result.add(new BigInteger(s.substring(7), 16));
            }
        }

        return result;
    }

    public static List<BigInteger> getN(final List<String> rawSE) {
        final List<BigInteger> result = new ArrayList<>();

        for (final String s : rawSE) {
            if (s.charAt(0) == 'N') {
                result.add(new BigInteger(s.substring(7), 16));
            }
        }

        return result;
    }
}
