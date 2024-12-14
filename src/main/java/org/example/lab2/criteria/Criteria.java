package org.example.lab2.criteria;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;
import static org.example.lab2.analyzer.BigramFrequencyAnalyzer.*;
import static org.example.lab2.analyzer.LetterFrequencyAnalyzer.*;

public class Criteria {
    private static final int criteria20LetterLimit = 50_000;
    private static final int criteria20BigramLimit = 30;

    private static final int criteria21LetterLimit = 50_000;
    private static final int criteria21BigramLimit = 100;
    private static final int criteria21LetterKf = 2;
    private static final int criteria21BigramKf = 700;

    private static final int criteria22LetterLimit = 13225;
    private static final int criteria22BigramLimit = 0;
    private static final int criteria22LetterKf = 4;
    private static final int criteria22BigramKf = 2;


    public static int criteriaZero(final List<String> texts, final String mainText, final int exp, final int FP) {
        int falseCounter = 0; // H1

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, criteria20LetterLimit);

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
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, criteria20BigramLimit);

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
        int falseCounter = 0;

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, criteria21LetterLimit);

            for (final String text : texts) {
                final Map<Character, Integer> aaf = new HashMap<>();

                for (int j = 0; j < (texts.getFirst()).length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= criteria21LetterKf) {
                    falseCounter++;
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, criteria21BigramLimit);

            for (final String text : texts) {
                final HashMap<String, Integer> aaf = new HashMap<>();

                for (int j = 0; j++ < (texts.getFirst()).length() - 2; j += 2) {
                    final String temp = text.substring(j, j + 2);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= criteria21BigramKf) {
                    falseCounter++;
                }
            }
        }

        return FP == 1 ? texts.size() - falseCounter : falseCounter;
    }

    public static int criteriaTwo(final List<String> texts, final String mainText, final int exp, final int FP) {
        final AtomicInteger falseCounter = new AtomicInteger(0);

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, criteria22LetterLimit);

            texts.parallelStream().forEach(text -> {
                final Map<Character, Integer> tfrq = getPopularLetterFrequencies(text, 0);

                for (int j = 0; j < text.length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp) && tfrq.get(temp) < criteria22LetterKf) {
                        falseCounter.incrementAndGet();
                        break;
                    }
                }
            });
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, criteria22BigramLimit);

            texts.parallelStream().forEach(text -> {
                final Map<String, Integer> tfrq = getPopularBigramFrequencies(text, 0);

                for (int j = 0; j++ < texts.getFirst().length() - 2; j += 2) {
                    final String temp = text.substring(j, j + 2);

                    if (afrq.containsKey(temp) && tfrq.get(temp) < criteria22BigramKf) {
                        falseCounter.incrementAndGet();
                        break;
                    }
                }
            });
        }

        return FP == 1 ? texts.size() - falseCounter.get() : falseCounter.get();
    }

    public static int criteriaThree(final List<String> texts, final String mainText, final int exp, final int FP) {
        final AtomicInteger falseCounter = new AtomicInteger(0);

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, criteria22LetterLimit);
            int sum = 0;

            for (String text : texts) {
                final Map<Character, Integer> tfrq = getPopularLetterFrequencies(text, 0);

                for (int j = 0; j < text.length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp)) {
                        sum += tfrq.get(temp);
                    }
                }

                if (sum < text.length() * 2) {
                    falseCounter.incrementAndGet();
                }

                sum = 0;
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, criteria22BigramLimit);
            int sum = 0;

            for (final String text : texts) {
                final Map<String, Integer> tfrq = getPopularBigramFrequencies(text, 0);

                for (int j = 0; j < text.length(); j++) {
                    String lTemp = text.substring(j, j + 2);

                    if (afrq.containsKey(lTemp)) {
                        sum += tfrq.get(lTemp);
                    }
                }

                if (sum < text.length() * 1.01) {
                    falseCounter.incrementAndGet();

                }

                sum = 0;
            }


        }

        return FP == 1 ? texts.size() - falseCounter.get() : falseCounter.get();
    }

    public static int criteriaFour(final List<String> texts, final String mainText, final int exp, final int FP) {
        final AtomicInteger falseCounter = new AtomicInteger(0);

        if (exp == 1) {
            final double mainIndex = findLetterIndex(mainText, getLetterFrequencies(mainText));

            for (final String text : texts) {
                final double index = findLetterIndex(text, getLetterFrequencies(text));

                if (abs(mainIndex - index) <= 18.53) {
                    falseCounter.incrementAndGet();
                }
            }
        }

        if (exp == 2) {
            final double mainIndex = findBigramIndex(mainText, getBigramFrequencies(mainText));

            Map<Double, Integer> map = new HashMap<>();

            for (final String text : texts) {
                final double index = findBigramIndex(text, getBigramFrequencies(text));

                map.put(Math.abs(mainIndex - index), map.getOrDefault(Math.abs(mainIndex - index), 0) + 1);

                if (abs(mainIndex - index) <= 1314.7825) {
                    falseCounter.incrementAndGet();
                }
            }
        }

        return FP == 1 ? texts.size() - falseCounter.get() : falseCounter.get();
    }

    public static int criteriaFive(final List<String> texts, final String mainText, final int exp, final int FFPP) {
        return 0;
    }

    public static int criteriaStructure(final List<String> texts, final String mainText, final int exp, final int FFPP) {
        return 0;
    }
}
