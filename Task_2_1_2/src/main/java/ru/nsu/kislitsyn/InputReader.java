package ru.nsu.kislitsyn;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
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
