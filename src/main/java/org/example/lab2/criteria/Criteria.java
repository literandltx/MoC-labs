package org.example.lab2.criteria;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.Deflater;

import static java.lang.Math.abs;
import static org.example.lab2.analyzer.BigramFrequencyAnalyzer.*;
import static org.example.lab2.analyzer.LetterFrequencyAnalyzer.*;

public class Criteria {
    public static int criteriaZero(final List<String> texts, final String mainText, final int exp, final int FP) {
        int falseCounter = 0; // H1

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, 15_000);

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
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, 1);

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
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, 15_000);

            for (final String text : texts) {
                final Map<Character, Integer> aaf = new HashMap<>();

                for (int j = 0; j < (texts.getFirst()).length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= 1) {
                    falseCounter++;
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, 100);

            for (final String text : texts) {
                final HashMap<String, Integer> aaf = new HashMap<>();

                for (int j = 0; j++ < (texts.getFirst()).length() - 2; j += 2) {
                    final String temp = text.substring(j, j + 2);

                    if (afrq.containsKey(temp)) {
                        aaf.put(temp, 1);
                    }
                }

                if (abs(afrq.size() - aaf.size()) <= 100) {
                    falseCounter++;
                }
            }
        }

        return FP == 1 ? texts.size() - falseCounter : falseCounter;
    }

    public static int criteriaTwo(final List<String> texts, final String mainText, final int exp, final int FP) {
        final AtomicInteger falseCounter = new AtomicInteger(0);

        if (exp == 1) {
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, 15_000);

            texts.parallelStream().forEach(text -> {
                final Map<Character, Integer> tfrq = getPopularLetterFrequencies(text, 0);

                for (int j = 0; j < text.length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp) && tfrq.get(temp) < 1) {
                        falseCounter.incrementAndGet();
                        break;
                    }
                }
            });
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, 600);

            texts.parallelStream().forEach(text -> {
                final Map<String, Integer> tfrq = getPopularBigramFrequencies(text, 0);

                for (int j = 0; j++ < texts.getFirst().length() - 2; j += 2) {
                    final String temp = text.substring(j, j + 2);

                    if (afrq.containsKey(temp) && tfrq.get(temp) < 2) {
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
            final Map<Character, Integer> afrq = getPopularLetterFrequencies(mainText, 50_000);
            int sum = 0;

            for (String text : texts) {
                final Map<Character, Integer> tfrq = getPopularLetterFrequencies(text, 0);

                for (int j = 0; j < text.length(); j++) {
                    final char temp = text.charAt(j);

                    if (afrq.containsKey(temp)) {
                        sum += tfrq.get(temp);
                    }
                }

                if ((double) sum / 100 < text.length() * 2.94) {
                    falseCounter.incrementAndGet();
                }

                sum = 0;
            }
        }

        if (exp == 2) {
            final Map<String, Integer> afrq = getPopularBigramFrequencies(mainText, 10);
            int sum = 0;

            for (final String text : texts) {
                final Map<String, Integer> tfrq = getPopularBigramFrequencies(text, 0);

                for (int j = 0; j < text.length() - 1; j++) {
                    String lTemp = text.substring(j, j + 2);

                    if (afrq.containsKey(lTemp)) {
                        sum += tfrq.get(lTemp);
                    }
                }

                if (sum < text.length() * 7.05) {
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

                if (abs(mainIndex - index) <= 18.5459) {
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

                if (abs(mainIndex - index) <= 1314.78584) {
                    falseCounter.incrementAndGet();
                }
            }
        }

        return FP == 1 ? texts.size() - falseCounter.get() : falseCounter.get();
    }

    public static int criteriaFive(final List<String> texts, final String mainText, final int exp, final int FP) {
        AtomicInteger falseCounter = new AtomicInteger(0);

        if (exp == 1) {
            final Map<Character, Integer> frq = getPopularLetterFrequencies(mainText, 15_000);
            int sum;

            for (final String text : texts) {
                final Map<Character, Integer> brf = new HashMap<>();

                for (int j = 0; j < text.length(); j++) {
                    if (frq.containsKey(text.charAt(j))) {
                        brf.put(text.charAt(j), 1);
                    }
                }

                sum = frq.size() - brf.size();
                int threshold = (int) Math.ceil(frq.size() * 0.18); // (0..1)
                if (sum < threshold) {
                    falseCounter.incrementAndGet();
                }
            }
        }

        if (exp == 2) {
            final Map<String, Integer> frq = getPopularBigramFrequencies(mainText, 10);
            int sum;

            for (final String text : texts) {
                final Map<String, Integer> brf = new HashMap<>();

                for (int j = 0; j++ < text.length() - 2; j += 2) {
                    if (frq.containsKey(text.substring(j, j + 2))) {
                        brf.put(text.substring(j, j + 2), 1);
                    }
                }

                sum = frq.size() - brf.size();
                int threshold = (int) Math.ceil(frq.size() * 0.11);
                if (sum < threshold) {
                    falseCounter.incrementAndGet();
                }
            }
        }

        return FP == 1 ? texts.size() - falseCounter.get() : falseCounter.get();
    }

    public static int criteriaStructure(final List<String> texts) {
        final AtomicInteger falseCounter = new AtomicInteger(0);
        int compressedLength;

        for (final String text : texts) {
            final byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);

            final Deflater deflater = new Deflater();
            deflater.setInput(textBytes);
            deflater.finish();

            byte[] compressedData = new byte[textBytes.length];
            compressedLength = deflater.deflate(compressedData);
            deflater.end();

//            double mean =   20.0 / text.length();
//            double std  =   0.01 / text.length();
//            double mean =  128.88 / text.length();
//            double std  =    3.69 / text.length();
//            double mean =  795.38 / text.length();
//            double std  =   45.14 / text.length();
            double mean = 6247.52 / text.length();
            double std  =  455.22 / text.length();

            double minK = (mean - std * 3) * text.length();
            double maxK = (mean + std * 3)  * text.length();

            if (!(compressedLength > minK) || !(compressedLength < maxK)) {
                continue;
            }
            falseCounter.incrementAndGet();
        }

        return falseCounter.get();
    }

    public static List<Integer> searchCriteriaStructure(final List<String> texts) {
        final List<Integer> values = new ArrayList<>();

        for (final String text : texts) {
            final byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            final Deflater deflater = new Deflater();

            deflater.setInput(textBytes);
            deflater.finish();

            final byte[] compressedData = new byte[textBytes.length];
            final int compressedLength = deflater.deflate(compressedData);
            deflater.end();

            values.add(compressedLength);
//            System.out.println(text.length() + " " + compressedLength);
        }

        return values;
    }
}
