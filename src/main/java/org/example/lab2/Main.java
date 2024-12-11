package org.example.lab2;

import java.util.*;

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

        // to remove
        String key1 = "ш";
        String key2 = "шість";
        String key3 = "шістнадцять";

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
    }

    private static void perform23VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
    }

    private static void perform40VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
    }

    private static void perform40VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
    }

    private static void perform50VigenereLetter(final String text, final Map<Integer, Integer> param, final String key) {
    }

    private static void perform50VigenereBigram(final String text, final Map<Integer, Integer> param, final String key) {
    }

    public static void perform(final String text, Map<Integer, Integer> param) {
        String key1 = "ш";
        String key2 = "шість";
        String key3 = "шістнадцять";

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
    }
}
