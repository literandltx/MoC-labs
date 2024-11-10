package org.example.lab2;

import java.util.Map;

import static org.example.EntropyHelper.entropyNGram;
import static org.example.lab2.util.ConsoleUtil.displayFrequencies;
import static org.example.lab2.BigramFrequencyAnalyzer.*;
import static org.example.lab2.LetterFrequencyAnalyzer.*;
import static org.example.lab2.util.FileUtils.readFile;
import static org.example.lab2.util.TextUtils.processText;

public class Main {
    private static final String filePath = "src/main/java/org/example/lab2/data/Zapiski_ykrajnskogo_samashedshogo.txt";

    public static void main(String[] args) {
        // init
        String rawText = readFile(filePath);
        String text = processText(rawText);

//        System.out.println(text);

//        displayLetterFrequencies(getLetterFrequencies(text));
        Map<Character, Double> normalizeLetterFrequencies = getNormalizeLetterFrequencies(getLetterFrequencies(text));
        Map<String, Double> normalizeBigramFrequencies = getNormalizeBigramFrequencies(getBigramFrequencies(text));
//        displayFrequencies(normalizeBigramFrequencies);
//        displayFrequencies(normalizeLetterFrequencies);
        System.out.println(entropyNGram(normalizeLetterFrequencies, 1));
        System.out.println(entropyNGram(normalizeBigramFrequencies, 2));
    }
}
