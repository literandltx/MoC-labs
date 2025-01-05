package org.example.lab2;

import java.util.*;

import static org.example.lab2.cipher.AffineCipher.affine;
import static org.example.lab2.cipher.SequenceGenerator.generateCorrelationSequence;
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

        performStructure(text, param,  key1);

//        testVigenereAffine(text, param, key1);
//        testVigenereAffine(text, param, key2);
//        testVigenereAffine(text, param, key3);
    }

    private static void testVigenereAffine(final String text, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "CH0l1", "CH1l1", "CH0l2", "CH1l2");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(text, entry.getValue(), entry.getKey());
//            final ArrayList<String> cipher1 = vigenere(texts, key);
//            final ArrayList<String> cipher2 = new ArrayList<>(cipher1);
//            final List<String> cipher1 = affine(texts, 11, 17, 1);
//            final List<String> cipher2 = affine(texts, 11, 17, 2);
//            final List<String> cipher1 = generatedSequence(entry.getKey(), entry.getValue(), 1);
//            final List<String> cipher2 = generatedSequence(entry.getKey(), entry.getValue(), 2);
            final List<String> cipher1 = generateCorrelationSequence(entry.getKey(), entry.getValue(), 1);
            final List<String> cipher2 = generateCorrelationSequence(entry.getKey(), entry.getValue(), 2);

            int size = texts.size();

            int cH0ZeroL1 = criteriaZero(cipher1, text, 1, 1);
            int cH0ZeroL2 = criteriaZero(cipher2, text, 2, 1);
            int cH0OneL1 = criteriaOne(cipher1, text, 1, 1);
            int cH0OneL2 = criteriaOne(cipher2, text, 2, 1);
            int cH0TwoL1 = criteriaTwo(cipher1, text, 1, 1);
            int cH0TwoL2 = criteriaTwo(cipher2, text, 2, 1);
            int cH0ThreeL1 = criteriaThree(cipher1, text, 1, 1);
            int cH0ThreeL2 = criteriaThree(cipher2, text, 2, 1);
            int cH0FourL1 = criteriaFour(cipher1, text, 1, 1);
            int cH0FourL2 = criteriaFour(cipher2, text, 2, 1);
            int cH0FiveL1 = criteriaFive(cipher1, text, 1, 1);
            int cH0FiveL2 = criteriaFive(cipher2, text, 2, 1);

            printResult(entry, cH0ZeroL1, size, cH0ZeroL2);
            printResult(entry, cH0OneL1, size, cH0OneL2);
            printResult(entry, cH0TwoL1, size, cH0TwoL2);
            printResult(entry, cH0ThreeL1, size, cH0ThreeL2);
            printResult(entry, cH0FourL1, size, cH0FourL2);
            printResult(entry, cH0FiveL1, size, cH0FiveL2);

            System.out.println();
        }
    }

    private static void printResult(Map.Entry<Integer, Integer> entry, int pH0L1, int size, int cH0L2) {
        int percentageCH0L1 = (int) (100 * Math.floor((double) pH0L1 / size * 100) / 100.0);
        int percentageCH1L1 = (int) (100 * Math.floor((double) (size - pH0L1) / size * 100) / 100.0);
        int percentageCH0L2 = (int) (100 * Math.floor((double) cH0L2 / size * 100) / 100.0);
        int percentageCH1L2 = (int) (100 * Math.floor((double) (size - cH0L2) / size * 100) / 100.0);

        System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d\n", entry.getKey(), entry.getValue(), percentageCH0L1, percentageCH1L1, percentageCH0L2, percentageCH1L2);
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
            final List<String> plain = getSequentialSubstrings(text, entry.getValue(), entry.getKey());
//            List<String> cipher = generatedSequence(entry.getValue(), entry.getKey(), 1);
            List<String> cipher = affine(plain, 11, 17, 1);

            int pH01 = criteriaStructure(plain);
            int cH01 = criteriaStructure(cipher);

            printResult(entry, pH01, plain.size(), cH01);
            System.out.println();
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
