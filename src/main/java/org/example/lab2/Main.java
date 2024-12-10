package org.example.lab2;

import java.util.*;

import static org.example.lab2.cipher.VigenereCipher.vigenere;
import static org.example.lab2.criteria.Criteria.criteriaZero;
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
        param.put(    10, 10_000);
        param.put(   100, 10_000);
        param.put( 1_000, 10_000);
        param.put(10_000, 1_000 );

//        perform20VigenereLetter(text, param, "ш");
//        perform20VigenereLetter(text, param, "шість");
//        perform20VigenereLetter(text, param, "шістнадцять");
//
//        perform20VigenereBigram(text, param, "ш");
//        perform20VigenereBigram(text, param, "шість");
//        perform20VigenereBigram(text, param, "шістнадцять");
    }

    private static void perform20VigenereLetter(final String mainText, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(mainText, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaZero(texts, mainText, 1, 1);
            int pH1 = criteriaZero(texts, mainText, 1, 0);
            int cH0 = criteriaZero(vigenere, mainText, 1, 1);
            int cH1 = criteriaZero(vigenere, mainText, 1, 0);

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }

    private static void perform20VigenereBigram(final String mainText, final Map<Integer, Integer> param, final String key) {
        System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s%n", "LEN", "NUM", "PH0", "PH1", "CH0", "CH1");

        for (final var entry : param.entrySet()) {
            final List<String> texts = getSequentialSubstrings(mainText, entry.getValue(), entry.getKey());

            final ArrayList<String> vigenere = vigenere(texts, key);

            int L = entry.getKey();
            int N = entry.getValue();

            int pH0 = criteriaZero(texts, mainText, 2, 1);
            int pH1 = criteriaZero(texts, mainText, 2, 0);
            int cH0 = criteriaZero(vigenere, mainText, 2, 1);
            int cH1 = criteriaZero(vigenere, mainText, 2, 0);

            System.out.printf("%-8d %-8d %-8d %-8d %-8d %-8d%n", L, N, pH0, pH1, cH0, cH1);
        }

        System.out.println();
    }
}
