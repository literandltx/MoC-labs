package org.example.lab1.util;

import java.util.List;

public class ConsoleUtil {

    public static void showList(List<?> list) {
        list.forEach(System.out::println);
    }

    public static void showTable(List<double[]> data) {
        for (double[] array : data) {
            for (double value : array) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

}
