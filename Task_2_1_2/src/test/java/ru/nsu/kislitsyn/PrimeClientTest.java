package ru.nsu.kislitsyn;

import org.junit.jupiter.api.Test;

class PrimeClientTest {
    @Test
    void test() {
        String[] args = null;
        PrimeClient.main(args);
        PrimeClient.main(args);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        PrimeServer.main(args);
    }
}