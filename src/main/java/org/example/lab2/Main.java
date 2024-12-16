package org.example.lab2;

import java.util.*;

import static org.example.lab2.cipher.SequenceGenerator.generatedSequence;
import static org.example.lab2.cipher.VigenereCipher.vigenere;
import static org.example.lab2.criteria.Criteria.*;
import static org.example.lab2.util.FileUtils.readFile;
import static org.example.lab2.util.TextUtils.createBigramAlphabet;
import static org.example.lab2.util.TextUtils.getSequentialSubstrings;
import static org.example.lab2.util.TextUtils.processText;

public class Main {
    public static final String ALPHABET = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя";
    public static final List<String> BIGRAM_ALPHABET = createBigramAlphabet();

    private static final List<String> filePaths = Arrays.asList(
            "src/main/java/org/example/lab2/data/Zapiski_ykrajnskogo_samashedshogo.txt",
            "src/main/java/org/example/lab2/data/Vognem_i_mechem_1464462866.txt",
            "src/main/java/org/example/lab2/data/BorvamechivAStormofSwords_1428595049.txt",
            "src/main/java/org/example/lab2/data/Bibliya_1369054606.txt",
            "src/main/java/org/example/lab2/data/Quovadis_1411395539.txt",
            "src/main/java/org/example/lab2/data/Yerusalym_na_horakh.txt",
            "src/main/java/org/example/lab2/data/Inferno_1400698206.txt",
            "src/main/java/org/example/lab2/data/Tochka_obmany_1371139083.txt",
            "src/main/java/org/example/lab2/data/Vtrachenii_simvol_1371139594.txt"
    );

    public static void main(String[] args) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final String filePath : filePaths) {
            stringBuilder.append(processText(readFile(filePath)));
        }

        final String text = stringBuilder.toString();

        // L -- len
        // N -- num
        // map is L : N
        final Map<Integer, Integer> param = new TreeMap<>();
        param.put(10, 10_000);
        param.put(100, 10_000);
        param.put(1_000, 10_000);
        param.put(10_000, 1_000);

        String key1 = "ш";
        String key2 = "шість";
        String key3 = "шістнадцять";

        performStructure(text, param, key1);
    }

    private static void perform20VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaZero(texts, text, 1, 1);
            int pH1 = criteriaZero(texts, text, 1, 0);
            int cH0 = criteriaZero(vigenere, text, 1, 1);
            int cH1 = criteriaZero(vigenere, text, 1, 0);

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform20VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaZero(texts, text, 2, 1);
            int pH1 = criteriaZero(texts, text, 2, 0);
            int cH0 = criteriaZero(vigenere, text, 2, 1);
            int cH1 = criteriaZero(vigenere, text, 2, 0);

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform21VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaOne(texts, text, 1, 1);
            int pH1 = criteriaOne(texts, text, 1, 0);
            int cH0 = criteriaOne(vigenere, text, 1, 1);
            int cH1 = criteriaOne(vigenere, text, 1, 0);

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform21VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaOne(texts, text, 2, 1);
            int pH1 = criteriaOne(texts, text, 2, 0);
            int cH0 = criteriaOne(vigenere, text, 2, 1);
            int cH1 = criteriaOne(vigenere, text, 2, 0);

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform22VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaTwo(texts, text, 1, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaTwo(vigenere, text, 1, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform22VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaTwo(texts, text, 2, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaTwo(vigenere, text, 2, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform23VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaThree(texts, text, 1, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaThree(vigenere, text, 1, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform23VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaThree(texts, text, 2, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaThree(vigenere, text, 2, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform40VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaFour(texts, text, 1, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaFour(vigenere, text, 1, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform40VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaFour(texts, text, 2, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaFour(vigenere, text, 2, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform50VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaFive(texts, text, 1, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaFive(vigenere, text, 1, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform50VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaFive(texts, text, 2, 1);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaFive(vigenere, text, 2, 1);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void performStructure(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());
            List<String> generatedSequence = generatedSequence(entry.getValue(), entry.getKey(), 1);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaStructure(texts);
            int pH1 = texts.size() - pH0;
            int cH0 = criteriaStructure(generatedSequence);
            int cH1 = texts.size() - cH0;

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void performSearchParamStructCriteria(final List<String> texts, final Map<Integer, Integer> param) {
        List<Integer> pH0 = searchCriteriaStructure(texts);

        System.out.println("M: " + findMean(pH0));
        System.out.println("D: " + findSTD(pH0, findMean(pH0)));
    }

    public static void perform(final String text, final Map<Integer, Integer> param) {
        final String key1 = "ш";
        final String key2 = "шість";
        final String key3 = "шістнадцять";

        System.out.println("perform20VigenereLetter");
        perform20VigenereLetter(text, param, key1);
        perform20VigenereLetter(text, param, key2);
        perform20VigenereLetter(text, param, key3);

        System.out.println("perform20VigenereBigram");
        perform20VigenereBigram(text, param, key1);
        perform20VigenereBigram(text, param, key2);
        perform20VigenereBigram(text, param, key3);

        System.out.println("perform21VigenereLetter");
        perform21VigenereLetter(text, param, key1);
        perform21VigenereLetter(text, param, key2);
        perform21VigenereLetter(text, param, key3);

        System.out.println("perform21VigenereBigram");
        perform21VigenereBigram(text, param, key1);
        perform21VigenereBigram(text, param, key2);
        perform21VigenereBigram(text, param, key3);

        System.out.println("perform22VigenereLetter");
        perform22VigenereLetter(text, param, key1);
        perform22VigenereLetter(text, param, key2);
        perform22VigenereLetter(text, param, key3);

        System.out.println("perform22VigenereBigram");
        perform22VigenereBigram(text, param, key1);
        perform22VigenereBigram(text, param, key2);
        perform22VigenereBigram(text, param, key3);

        System.out.println("perform23VigenereLetter");
        perform23VigenereLetter(text, param, key1);
        perform23VigenereLetter(text, param, key2);
        perform23VigenereLetter(text, param, key3);

        System.out.println("perform23VigenereBigram");
        perform23VigenereBigram(text, param, key1);
        perform23VigenereBigram(text, param, key2);
        perform23VigenereBigram(text, param, key3);

        System.out.println("perform40VigenereLetter");
        perform40VigenereLetter(text, param, key1);
        perform40VigenereLetter(text, param, key2);
        perform40VigenereLetter(text, param, key3);

        System.out.println("perform40VigenereBigram");
        perform40VigenereBigram(text, param, key1);
        perform40VigenereBigram(text, param, key2);
        perform40VigenereBigram(text, param, key3);

        System.out.println("perform50VigenereLetter");
        perform50VigenereLetter(text, param, key1);
        perform50VigenereLetter(text, param, key2);
        perform50VigenereLetter(text, param, key3);

        System.out.println("perform50VigenereBigram");
        perform50VigenereBigram(text, param, key1);
        perform50VigenereBigram(text, param, key2);
        perform50VigenereBigram(text, param, key3);
    }

    public static double findMean(final List<Integer> values) {
        double sum = 0;

        for (int value : values) {
            sum += value;
        }

        return sum / values.size();
    }

    public static double findSTD(final List<Integer> values, final double mean) {
        double squared_diff_sum = 0;

        for (final int value : values) {
            squared_diff_sum += Math.pow(value - mean, 2);
        }

        return Math.sqrt(squared_diff_sum / values.size());
    }

    private static void printMeanStd(final String text, final Map<Integer, Integer> param) {
        performSearchParamStructCriteria(getSequentialSubstrings(text, 10_000, 10), param);
        performSearchParamStructCriteria(getSequentialSubstrings(text, 10_000, 100), param);
        performSearchParamStructCriteria(getSequentialSubstrings(text, 10_000, 1_000), param);
        performSearchParamStructCriteria(getSequentialSubstrings(text, 1_000, 10_000), param);
    }

    private static long measureExecutionTime(final Runnable method) {
        final long startTime = System.nanoTime();
        method.run();
        final long endTime = System.nanoTime();

        return endTime - startTime;
    }
}
