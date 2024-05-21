package ru.nsu.kislitsyn;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that reads numbers from file.
 */
public class InputReader {
    /**
     * Reads a list of numbers from inputStream.
     *
     * @param in inputStream with numbers.
     * @return list of numbers.
     */
    public static List<Integer> getNumbers(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            int n = scanner.nextInt();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                numbers.add(scanner.nextInt());
            }
            return numbers;
        }
    }
}
