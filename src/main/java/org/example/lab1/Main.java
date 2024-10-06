package org.example.lab1;

import java.util.List;

import static org.example.lab1.util.CsvFileUtil.readLineFromCsvFile;
import static org.example.lab1.util.CsvFileUtil.readTableFromCsvFile;

public class Main {
    public static void main(String[] args) {
        String plainTextPath = "src/main/java/org/example/lab1/data/prob_15.csv";
        String cipherTextPath = "src/main/java/org/example/lab1/data/table_15.csv";

        List<double[]> cipherText = readTableFromCsvFile(cipherTextPath);
        List<Double> plainTextProbabilities = readLineFromCsvFile(plainTextPath, 0);
        List<Double> keysProbabilities = readLineFromCsvFile(plainTextPath, 1);

        System.out.println(plainTextProbabilities);
        System.out.println(keysProbabilities);
        System.out.println();
        showTable(cipherText);
    }

    private static void showTable(List<double[]> data) {
        for (double[] array : data) {
            for (double value : array) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
