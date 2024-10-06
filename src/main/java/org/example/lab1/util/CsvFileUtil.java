package org.example.lab1.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileUtil {

    public static List<double[]> readTableFromCsvFile(final String filePath) {
        final List<double[]> result = new ArrayList<>();

        try {
            final List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (final String line : lines) {
                final String[] strings = line.split(",");
                final double[] doubles = new double[strings.length];

                for (int i = 0; i < strings.length; i++) {
                    doubles[i] = Double.parseDouble(strings[i].trim());
                }

                result.add(doubles);
            }
        } catch (final IOException e) {
            throw new RuntimeException("File Not Found");
        }

        return result;
    }

    public static List<Double> readLineFromCsvFile(final String filePath, final int lineNumber) {
        try {
            final List<String> lines = Files.readAllLines(Paths.get(filePath));

            if (lineNumber < 0 || lineNumber >= lines.size()) {
                throw new IllegalArgumentException("Line number is out of range");
            }

            final String line = lines.get(lineNumber);
            final String[] strings = line.split(",");
            final List<Double> doubles = new ArrayList<>();

            for (String str : strings) {
                doubles.add(Double.parseDouble(str.trim()));
            }

            return doubles;

        } catch (final IOException e) {
            throw new RuntimeException("File Not Found", e);
        }
    }

}
