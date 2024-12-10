package org.example.lab2.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static org.example.lab2.analyzer.BigramFrequencyAnalyzer.getPopularBigramFrequencies;
import static org.example.lab2.analyzer.LetterFrequencyAnalyzer.getPopularLetterFrequencies;

public class Criteria {
    private static final int criteriaZeroLetterLimit = 50_000;
    private static final int criteriaZeroBigramLimit = 30;

    private static final int criteriaFirstLetterLimit = 1000;
    private static final int criteriaFirstBigramLimit = 1000;
    private static final int criteriaFirstLetterKf = 1;
    private static final int criteriaFirstBigramKf = 200;

    public static int criteriaZero(final List<String> texts, final String mainText, final int exp, final int FP) {
        int falseCounter = 0; // H1

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, criteriaZeroLetterLimit);

            for (final String text : texts) {
                for (int j = 0; j < texts.getFirst().length(); j++) {
                    if (!afrq.containsKey(text.charAt(j))) {
                        falseCounter++;
                        break;
                    }
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, criteriaZeroBigramLimit);

            for (final String text : texts) {
                for (int j = 0; j++ < texts.getFirst().length() - 2; j += 2) {
                    if (!afrq.containsKey(text.substring(j, j + 2))) {
                        falseCounter++;
                        break;
                    }
                }
            }
        }

        return FP == 1 ? texts.size() - falseCounter : falseCounter;
    }

    public static int criteriaOne(final List<String> texts, final String mainText, final int exp, final int FP) {
        int trueCounter = 0;
        int falseCounter = 0;

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, criteriaFirstLetterLimit);

            for (final String text : texts) {
                final Map<Character, Integer> aaf = new HashMap<>();

                for (int j = 0; j < (texts.getFirst()).length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= criteriaFirstLetterKf) {
                    falseCounter++;
                }

                if (abs(afrq.size() - aaf.size()) > criteriaFirstLetterKf) {
                    trueCounter++;
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, criteriaZeroBigramLimit);

            for (final String text : texts) {
                final HashMap<String, Integer> aaf = new HashMap<>();

                for (int j = 0; j++ < (texts.getFirst()).length() - 2; j += 2) {
                    final String temp = text.substring(j, j + 2);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= criteriaFirstBigramKf) {
                    falseCounter++;
                }

                if (abs(afrq.size() - aaf.size()) > criteriaFirstBigramKf) {
                    trueCounter++;
                }
            }
        }

        return FP == 1 ? trueCounter : falseCounter;
    }

    public static int criteriaTwo(final List<String> texts, final String mainText, final int exp, final int FFPP) {
        return 0;
    }

    public static int criteriaThree(final List<String> texts, final String mainText, final int exp, final int FFPP) {
        return 0;
    }

}
