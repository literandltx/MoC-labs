package org.example.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        List<Double> probC = findCipherTextProbability(plainTextProbabilities, keysProbabilities, cipherTextTable);
        List<List<Double>> probMC = findOpenTextCipherTextProbability(plainTextProbabilities, keysProbabilities, cipherTextTable);
        List<List<Double>> probMifC = findOpenTextIfCiphertextProbability(probMC, probC);
        List<Double> DDF = findOptimalDeterministicDecisionFunction(probMifC);
        List<List<Double>> SDF = findOptimalStochasticDecisionFunction(probMifC);
        double averageLossesDDF = averageLosses(probMC, lossFuncDDF(DDF));
        double averageLossesSDF = averageLosses(probMC, lossFuncSDF(SDF));

        showList(probC);
        showTable(probMC, 4);
        showTable(probMifC, 4);
        showList(DDF);
        showTable(SDF, 4);
        System.out.println(averageLossesDDF);
        System.out.println(averageLossesSDF);
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

    // P(M, C) Method to calculate open text to cipher text probabilities
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

    // P(M | C) Method to calculate open text probabilities given ciphertext probabilities
    public static List<List<Double>> findOpenTextIfCiphertextProbability(
            final List<List<Double>> MC,
            final List<Double> C
    ) {
        final int n = MC.size();
        final List<List<Double>> prob = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            final List<Double> row = new ArrayList<>(Collections.nCopies(n, 0.0));
            prob.add(row);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (C.get(j) != 0) {
                    prob.get(i).set(j, prob.get(i).get(j) + MC.get(i).get(j) / C.get(j));
                }
            }
        }

        return prob;
    }

    public static List<Double> findOptimalDeterministicDecisionFunction(
            final List<List<Double>> probMIfC
    ) {
        final int n = probMIfC.size();
        final List<Double> result = new ArrayList<>(n);

        for (int c = 0; c < n; c++) {
            double optimalM = 0;
            double maxProb = probMIfC.getFirst().get(c);

            for (int m = 1; m < n; m++) {
                final double currentProb = probMIfC.get(m).get(c);

                if (currentProb > maxProb) {
                    maxProb = currentProb;
                    optimalM = m;
                }
            }

            result.add(optimalM);
        }

        return result;
    }

    public static List<List<Double>> findOptimalStochasticDecisionFunction(
            final List<List<Double>> probMIfC
    ) {
        final int n = probMIfC.size();
        final List<List<Double>> result = new ArrayList<>(n);

        for (int c = 0; c < n; c++) {
            result.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }

        for (int c = 0; c < n; c++) {
            final List<Integer> maxProbIds = new ArrayList<>();
            double maxProb = probMIfC.getFirst().get(c);

            for (int m = 0; m < n; m++) {
                final double currentProb = probMIfC.get(m).get(c);
                if (currentProb > maxProb) {
                    maxProb = currentProb;
                    maxProbIds.clear();
                    maxProbIds.add(m);
                } else if (currentProb == maxProb) {
                    maxProbIds.add(m);
                }
            }

            final double coef = 1.0 / maxProbIds.size();
            for (int id : maxProbIds) {
                result.get(c).set(id, coef);
            }
        }

        return result;
    }

    public static double averageLosses(
            final List<List<Double>> probMC,
            final List<List<Double>> lsFunc
    ) {
        double result = 0;

        for (int i = 0; i < lsFunc.size(); i++) {
            for (int j = 0; j < lsFunc.size(); j++) {
                result += probMC.get(i).get(j) * lsFunc.get(i).get(j);
            }
        }

        return result;
    }

    public static List<List<Double>> lossFuncDDF(
            final List<Double> ddf
    ) {
        final List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < ddf.size(); i++) {
            final List<Double> row = new ArrayList<>();

            for (int j = 0; j < ddf.size(); j++) {
                row.add(1.);
            }

            result.add(row);
        }

        for (int i = 0; i < ddf.size(); i++) {
            final double m = ddf.get(i);

            result.get((int) m).set(i, 0.);
        }

        return result;
    }

    public static List<List<Double>> lossFuncSDF(
            List<List<Double>> sdf
    ) {
        final List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < sdf.size(); i++) {
            final List<Double> row = new ArrayList<>();

            for (int j = 0; j < sdf.size(); j++) {
                row.add(0.0);
            }

            result.add(row);
        }

        for (int i = 0; i < sdf.size(); i++) {
            for (int j = 0; j < sdf.size(); j++) {
                for (int k = 0; k < sdf.size(); k++) {
                    if (k != j) {
                        result.get(j).set(i, result.get(j).get(i) + sdf.get(i).get(k));
                    }
                }
            }
        }

        return result;
    }
}
