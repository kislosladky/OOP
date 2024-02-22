import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class PrimeTest {
    @Test
    void easyTest() {
        assertTrue(Prime.notPrime(8));
    }

    @Test
    void easyTest2() {
        assertFalse(Prime.notPrime(11));
    }

    @Test
    void biggerNumberTest() {
        assertFalse(Prime.notPrime(20319251));
    }
}