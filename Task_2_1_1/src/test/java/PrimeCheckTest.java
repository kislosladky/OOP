import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeCheckTest {
    @Test
    void easyTest() {
        assertFalse(PrimeCheck.isPrime(8));
    }

    @Test
    void easyTest2() {
        assertTrue(PrimeCheck.isPrime(11));
    }

    @Test
    void biggerNumberTest() {
        assertTrue(PrimeCheck.isPrime(20319251));
    }
}