package ru.nsu.kislitsyn.heapsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

class HeapsortTest {

    @Test
    void sortOfRegularArray() {
        assertArrayEquals(new int[] {1, 2, 3, 4}, Heapsort.sort(new int[] {3, 1, 2, 4}));
    }

    @Test
    void sortOfArrayWithNegativeNumbers() {
        assertArrayEquals(new int[] {-1, 0, 1, 2, 3, 4},
                Heapsort.sort(new int[] {3, 1, 2, 4, 0, -1}));
    }

    @Test
    void sortOfReversedArray() {
        assertArrayEquals(new int[] {-1, 0, 1, 2, 3, 4},
                Heapsort.sort(new int[] {4, 3, 2, 1, 0, -1}));
    }

    @Test
    void sortOfSortedArray() {
        assertArrayEquals(new int[] {-1, 0, 1, 2, 3, 4},
                Heapsort.sort(new int[] {-1, 0, 1, 2, 3, 4}));
    }

    @Test
    void sortOfEmptyArray() {
        assertArrayEquals(new int[] {},
                Heapsort.sort(new int[] {}));
    }

    @Test
    void sortOfLargeArray() {
        int length = 1000;
        int min = -1000;
        int max = 1000;
        int[] arr = new int[length];
        int[] copyArr = new int[length];
        for (int i = 0; i < length; i++) {
            int number = ThreadLocalRandom.current().nextInt(min, max + 1);;
            arr[i] = number;
            copyArr[i] = number;
        }
        Arrays.sort(arr);
        assertArrayEquals(arr, Heapsort.sort(copyArr));
    }
}