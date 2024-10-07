package org.example.lab1.util;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

public class ConsoleUtil {

    public static void showList(List<?> list) {
        list.forEach(System.out::println);
    }

    public static void showTable(Collection<?> data, int decimalPlaces) {
        if (data == null || data.isEmpty()) {
            System.out.println("No data to display.");
            return;
        }

        DecimalFormat df = new DecimalFormat("#." + "0".repeat(Math.max(0, decimalPlaces)));

        int maxColumnWidth = 0;
        for (Object item : data) {
            if (item instanceof List<?> list) {
                for (Object value : list) {
                    maxColumnWidth = Math.max(maxColumnWidth, df.format(value).length());
                }
            } else if (item.getClass().isArray()) {
                int length = Array.getLength(item);
                for (int i = 0; i < length; i++) {
                    maxColumnWidth = Math.max(maxColumnWidth, df.format(Array.get(item, i)).length());
                }
            }
        }

        System.out.println("Table:");
//        System.out.println("-".repeat(maxColumnWidth * 3));

        for (Object item : data) {
            if (item instanceof List<?> list) {
                for (Object value : list) {
                    System.out.printf("%-" + maxColumnWidth + "s ", df.format(value));
                }
                System.out.println();
            } else if (item.getClass().isArray()) {
                int length = Array.getLength(item);
                for (int i = 0; i < length; i++) {
                    System.out.printf("%-" + maxColumnWidth + "s ", df.format(Array.get(item, i)));
                }
                System.out.println();
            } else {
                System.out.println("Element is neither a List nor an array.");
            }
        }
    }

}
