package ru.nsu.kislitsyn.heapsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class HeapsortTest {

    @Test
    void sortOfRegularArray() {
        assertArrayEquals(new int[]{1, 2, 3, 4}, Heapsort.sort(new int[]{3, 1, 2, 4}));
    }

    @Test
    void sortOfArrayWithNegativeNumbers() {
        assertArrayEquals(new int[]{-1, 0, 1, 2, 3, 4},
                Heapsort.sort(new int[]{3, 1, 2, 4, 0, -1}));
    }

    @Test
    void sortOfReversedArray() {
        assertArrayEquals(new int[]{-1, 0, 1, 2, 3, 4},
                Heapsort.sort(new int[]{4, 3, 2, 1, 0, -1}));
    }

    @Test
    void sortOfSortedArray() {
        assertArrayEquals(new int[]{-1, 0, 1, 2, 3, 4},
                Heapsort.sort(new int[]{-1, 0, 1, 2, 3, 4}));
    }

    @Test
    void sortOfEmptyArray() {
        assertArrayEquals(new int[]{}, Heapsort.sort(new int[]{}));
    }

    @Test
    void sortOfLargeArray() {
        int length = 1000000;
        int min = -1000;
        int max = 1000;
        int[] exp = new int[length];
        int[] actual = new int[length];
        for (int i = 0; i < length; i++) {
            int number = ThreadLocalRandom.current().nextInt(min, max + 1);
            ;
            exp[i] = number;
            actual[i] = number;
        }
        Arrays.sort(exp);
        assertArrayEquals(exp, Heapsort.sort(actual));
    }
}