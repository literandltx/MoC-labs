package org.example.lab1;

import java.util.*;

import static org.example.lab1.util.ConsoleUtil.showList;
import static org.example.lab1.util.ConsoleUtil.showTable;
import static org.example.lab1.util.CsvFileUtil.readLineFromCsvFile;
import static org.example.lab1.util.CsvFileUtil.readTableFromCsvFile;

public class Main {

    public static void main(String[] args) {
        String plainTextPath = "src/main/java/org/example/lab1/data/prob_15.csv";
        String cipherTextPath = "src/main/java/org/example/lab1/data/table_15.csv";

        List<double[]> cipherTextTable = readTableFromCsvFile(cipherTextPath);
        List<Double> plainTextProbabilities = readLineFromCsvFile(plainTextPath, 0);
        List<Double> keysProbabilities = readLineFromCsvFile(plainTextPath, 1);

        List<Double> C = findCipherTextProbability(plainTextProbabilities, keysProbabilities, cipherTextTable);
        List<List<Double>> MC = findOpenTextCipherTextProbability(plainTextProbabilities, keysProbabilities, cipherTextTable);

//        showList(C);
        showTable(MC, 4);

    }

    // P(C) Method to calculate cipher text probabilities
    public static List<Double> findCipherTextProbability(
            List<Double> plain,
            List<Double> key,
            List<double[]> cipherTable
    ) {
        final int n = plain.size();
        final int m = key.size();
        final List<Double> probCiphertext = new ArrayList<>(Collections.nCopies(n, 0.0));

        for (int i = 0; i < n; i++) {
            final double pMi = plain.get(i);
            for (int j = 0; j < m; j++) {
                final double pKj = key.get(j);
                final int c = (int) cipherTable.get(j)[i];

                probCiphertext.set(c, probCiphertext.get(c) + pKj * pMi);
            }
        }

        return probCiphertext;
    }

    // P(C, M) Method to calculate open text to cipher text probabilities
    public static List<List<Double>> findOpenTextCipherTextProbability(
            List<Double> plain,
            List<Double> key,
            List<double[]> cipherTable
    ) {
        final int n = plain.size();
        final int m = key.size();
        final List<List<Double>> probTable = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            probTable.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }

        for (int i = 0; i < n; i++) {
            final double pMi = plain.get(i);
            for (int j = 0; j < m; j++) {
                final double pKj = key.get(j);
                final int c = (int) cipherTable.get(j)[i];

                probTable.get(i).set(c, probTable.get(i).get(c) + pKj * pMi);
            }
        }

        return probTable;
    }
}
