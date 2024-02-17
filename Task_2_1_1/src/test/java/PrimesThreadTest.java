import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimesThreadTest {
    @Test
    void taskTest1() {
        Integer[] ints = new Integer[]{6, 8, 7, 13, 5, 9, 4};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(ints));
        Prime primes = new PrimesThread(6, numbers);
        assertTrue(primes.compute());
    }

    @Test
    void taskTest2() {
        Integer[] ints = new Integer[]{20319251, 6997901, 6997927, 6997937,
                17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(ints));
        Prime primes = new PrimesThread(6, numbers);
        assertFalse(primes.compute());
    }

    @Test
    void taskBigger() {
        Integer[] ints = new Integer[100000];
        Arrays.fill(ints, 20165149);
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(ints));
        Prime primes = new PrimesThread(6, numbers);
        assertFalse(primes.compute());
    }

    @Test
    void taskLarge() {
        long start = System.currentTimeMillis();
        Integer[] ints = new Integer[30000000];
        Arrays.fill(ints, 20165149);
//        ints[9020233] = 4;
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(ints));
        Prime primes = new PrimesThread(12, numbers);

        boolean answ = primes.compute();
        long finish = System.currentTimeMillis();
        System.out.println("The time of computing is " + (finish - start));
        assertFalse(answ);

    }
}