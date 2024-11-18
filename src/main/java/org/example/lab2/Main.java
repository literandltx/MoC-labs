package org.example.lab2;

import java.util.ArrayList;
import java.util.List;

import static org.example.lab2.util.FileUtils.readFile;
import static org.example.lab2.util.TextUtils.processText;

public class Main {
    private static final String filePath = "src/main/java/org/example/lab2/data/Zapiski_ykrajnskogo_samashedshogo.txt";

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

//        List<String> subText = getSequentialSubstrings(text, X, L);
//        ArrayList<String> vigenere = vigenere(subText, "secretsecretkeyword");

//        ArrayList<String> list = new ArrayList<>(List.of("text_text_text"));
//        ArrayList<String> affine = affine(list, "3", "4", 1);
//        System.out.println(affine);
    }
}
