package org.example.lab2;

import org.example.lab2.cipher.SequenceGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.example.lab2.cipher.AffineCipher.*;
import static org.example.lab2.cipher.VigenereCipher.vigenere;
import static org.example.lab2.criteria.Criteria.criteriaOne;
import static org.example.lab2.criteria.Criteria.criteriaZero;
import static org.example.lab2.util.FileUtils.readFile;
import static org.example.lab2.util.TextUtils.getSequentialSubstrings;
import static org.example.lab2.util.TextUtils.processText;

public class Main {
//    private static final String filePath = "src/main/java/org/example/lab2/data/Zapiski_ykrajnskogo_samashedshogo.txt";
//    private static final String filePath = "src/main/java/org/example/lab2/data/Vognem_i_mechem_1464462866.txt";
//    private static final String filePath = "src/main/java/org/example/lab2/data/BorvamechivAStormofSwords_1428595049.txt";
    private static final String filePath = "src/main/java/org/example/lab2/data/Bibliya_1369054606.txt";

    public static final String ALPHABET = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя";
    public static final List<String> BIGRAM_ALPHABET = createBigramAlphabet();

    private static List<String> createBigramAlphabet() {
        final List<String> allBi = new ArrayList<>(ALPHABET.length() * ALPHABET.length());

        for (int i = 0; i < ALPHABET.length(); i++) {
            for (int j = 0; j < ALPHABET.length(); j++) {
                allBi.add(String.valueOf(ALPHABET.charAt(i)) + ALPHABET.charAt(j));
            }
        }

        return allBi;
    }

    public static void main(String[] args) {
        // init
        String rawText = readFile(filePath);
        String text = processText(rawText);

        // text number
//        List<Integer> listN = new ArrayList<>(List.of(1_000, 10_000));
//        int N = 1_000;
        int N = 10_000;

        // text len
//        List<Integer> listL = new ArrayList<>(List.of(10, 100, 1_000, 10_000));
//        int L = 10;
        int L = 100;
//        int L = 1_000;
//        int L = 10_000;
//        System.out.println(text);

//        displayLetterFrequencies(getLetterFrequencies(text));
//        Map<Character, Double> normalizeLetterFrequencies = getNormalizeLetterFrequencies(getLetterFrequencies(text));
//        Map<String, Double> normalizeBigramFrequencies = getNormalizeBigramFrequencies(getBigramFrequencies(text));
//        displayFrequencies(normalizeBigramFrequencies);
//        displayFrequencies(normalizeLetterFrequencies);
//        System.out.println(entropyNGram(normalizeLetterFrequencies, 1));
//        System.out.println(entropyNGram(normalizeBigramFrequencies, 2));

//        List<String> list = new ArrayList<>(List.of("суперпупертекст"));

        System.out.println(text.length());
        List<String> texts = getSequentialSubstrings(text, N, L);

        System.out.println(texts.size());
        System.out.println("------");
        System.out.println();

//        ArrayList<String> vigenere = vigenere(list, "пароль");
//        System.out.println(vigenere);

//        List<String> affine = affine(list, 5, 7, 2);
//        System.out.println(affine);

//        List<String> sequence = SequenceGenerator.generatedSequence(L, 1, 1);
//        List<String> correlationSequence = SequenceGenerator.generateCorrelationSequence(L, 1, 1);
//        System.out.println(sequence);
//        System.out.println(correlationSequence);

        ArrayList<String> vigenere = vigenere(texts, "шість");

//        System.out.println("H0: " + criteriaZero(texts, text, 1, 1) + " H1: " + criteriaZero(texts, text, 1, 0));
        System.out.println("H0: " + criteriaZero(texts, text, 2, 1) + " H1: " + criteriaZero(texts, text, 2, 0));
        System.out.println();
//        System.out.println("H0: " + criteriaZero(vigenere, text, 1, 1) + " H1: " + criteriaZero(vigenere, text, 1, 0));
        System.out.println("H0: " + criteriaZero(vigenere, text, 2, 1) + " H1: " + criteriaZero(vigenere, text, 2, 0));
        System.out.println();

//        System.out.println(criteriaOne(texts, text, 1, 1));
//        System.out.println(criteriaOne(texts, text, 1, 2));
//        System.out.println(criteriaOne(texts, text, 2, 1));
//        System.out.println(criteriaOne(texts, text, 2, 2));
//        System.out.println();
//        System.out.println(criteriaOne(vigenere, text, 1, 1));
//        System.out.println(criteriaOne(vigenere, text, 1, 2));
//        System.out.println(criteriaOne(vigenere, text, 2, 1));
//        System.out.println(criteriaOne(vigenere, text, 2, 2));
//        System.out.println();
    }
}
