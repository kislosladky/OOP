package ru.nsu.kislitsyn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeServerTest {
    @Test
    void test() {
        String[] args = null;
        PrimeServer.main(args);
        PrimeServer.main(args);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        PrimeClient.main(args);
    }
}