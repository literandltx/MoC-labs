package org.example.lab3.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {
    public static String readFile(final String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (final IOException e) {
            throw new RuntimeException("Cannot find file: " + filePath);
        }
    }

    public static List<String> readFileAsList(final String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (final IOException e) {
            throw new RuntimeException("Cannot find file: " + filePath, e);
        }
    }
}
