package org.example.lab2;

import org.example.lab2.cipher.SequenceGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.example.lab2.cipher.AffineCipher.*;
import static org.example.lab2.cipher.VigenereCipher.vigenere;
import static org.example.lab2.util.FileUtils.readFile;
import static org.example.lab2.util.TextUtils.getSequentialSubstrings;
import static org.example.lab2.util.TextUtils.processText;

public class Main {
    private static final String filePath = "src/main/java/org/example/lab2/data/Zapiski_ykrajnskogo_samashedshogo.txt";

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

        int X = 10; /* 10 */ /* 100 */ /* 1_000 */ /* 10_000 */
        int L = 10_000; /* 1_000 */

//        System.out.println(text);

//        displayLetterFrequencies(getLetterFrequencies(text));
//        Map<Character, Double> normalizeLetterFrequencies = getNormalizeLetterFrequencies(getLetterFrequencies(text));
//        Map<String, Double> normalizeBigramFrequencies = getNormalizeBigramFrequencies(getBigramFrequencies(text));
//        displayFrequencies(normalizeBigramFrequencies);
//        displayFrequencies(normalizeLetterFrequencies);
//        System.out.println(entropyNGram(normalizeLetterFrequencies, 1));
//        System.out.println(entropyNGram(normalizeBigramFrequencies, 2));

        List<String> list = new ArrayList<>(List.of("суперпупертекст"));

//        2.0, 2.2, 2.2, 2.3, 4.0, 5.0
//        List<String> subText = getSequentialSubstrings(text, X, L);
//        ArrayList<String> vigenere = vigenere(list, "пароль");
//        System.out.println(vigenere);

//        List<String> affine = affine(list, 5, 7, 2);
//        System.out.println(affine);

//        List<String> sequence = SequenceGenerator.generatedSequence(L, 1, 1);
//        List<String> correlationSequence = SequenceGenerator.generateCorrelationSequence(L, 1, 1);
//        System.out.println(sequence);
//        System.out.println(correlationSequence);
    }
}
