package ru.nsu.kislitsyn.heapsort;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
class HeapsortTest {

    @BeforeAll
    static void begin() {
        System.out.println("Testing is started");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test is passed");
    }

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
    void sortOfBigArray() {
        assertArrayEquals(new int[] {},
                Heapsort.sort(new int[] {}));
    }



    @AfterAll
    static void end() {
        System.out.println("Testing is finished");
    }
}