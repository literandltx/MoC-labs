package org.example.lab2.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static org.example.lab2.analyzer.BigramFrequencyAnalyzer.getPopularBigramFrequencies;
import static org.example.lab2.analyzer.LetterFrequencyAnalyzer.getPopularLetterFrequencies;

public class Criteria {
    private static final int letterLimit = 10000;
    private static final int bigramLimit = 10;

    public static int criteriaZero(final ArrayList<String> texts, final String mainText, final int exp, final int FFPP) {
        int trueCounter = 0;
        int falseCounter = 0;

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, letterLimit);

            for (final String text : texts) {
                for (int j = 0; j < texts.getFirst().length(); j++) {
                    if (!afrq.containsKey(text.charAt(j))) {
                        falseCounter++;
                        break;
                    }

                    if (j == ((texts.getFirst()).length() - 1)) {
                        trueCounter++;
                        break;
                    }
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, bigramLimit);

            for (final String text : texts) {
                for (int j = 0; j++ < texts.getFirst().length(); j += 2) {
                    if (!afrq.containsKey(text.substring(j, j + 2))) {
                        falseCounter++;
                        break;
                    }

                    if (j == ((texts.getFirst()).length() - 2)) {
                        trueCounter++;
                        break;
                    }
                }
            }
        }

        return FFPP == 1 ? trueCounter : falseCounter;
    }

    public static int criteriaOne(final ArrayList<String> texts, final String mainText, final int exp, final int FFPP) {
        int trueCounter = 0;
        int falseCounter = 0;

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, letterLimit);

            for (final String text : texts) {
                final Map<Character, Integer> aaf = new HashMap<>();

                for (int j = 0; j < (texts.getFirst()).length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= 3) {
                    falseCounter++;
                }

                if (abs(afrq.size() - aaf.size()) > 3) {
                    trueCounter++;
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, bigramLimit);

            for (final String text : texts) {
                final HashMap<String, Integer> aaf = new HashMap<>();

                for (int j = 0; j++ < (texts.getFirst()).length(); j += 2) {
                    final String temp = text.substring(j, j + 2);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= 200) {
                    falseCounter++;
                }

                if (abs(afrq.size() - aaf.size()) > 200) {
                    trueCounter++;
                }
            }
        }

        return FFPP == 1 ? trueCounter : falseCounter;
    }

    public static int criteriaTwo() {
        return 0;
    }

    public static int criteriaThree() {
        return 0;
    }

}
