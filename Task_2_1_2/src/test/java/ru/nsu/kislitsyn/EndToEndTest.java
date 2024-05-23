package ru.nsu.kislitsyn;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;


class EndToEndTest {
    @Test
    void falseTest() throws InterruptedException {
        Thread clientRunner = new Thread(() -> PrimeClient.main(null));
        clientRunner.start();
        Thread.sleep(1000);
        PrimeServer server = new PrimeServer();
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 11, 13, 17));
        assertFalse(server.work(input));
        clientRunner.join();
    }

    @Test
    void trueTest() throws InterruptedException {
        Thread clientRunner = new Thread(() -> PrimeClient.main(null));
        clientRunner.start();
        Thread.sleep(1000);
        PrimeServer server = new PrimeServer();
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 12, 13, 17));
        assertTrue(server.work(input));
        clientRunner.join();
    }
}