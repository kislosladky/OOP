package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void sort() {
        assertArrayEquals(new int[] {1, 2, 3, 4}, Heapsort.sort(new int[] {3, 1, 2, 4}));
    }

    @Test
    void sort2() {
        assertArrayEquals(new int[] {-1, 0, 1, 2, 3, 4}, Heapsort.sort(new int[] {3, 1, 2, 4, 0, -1}));
    }

    @AfterAll
    static void end(){
        System.out.println("Testing is finished");
    }
}