package ru.nsu.kislitsyn.polynomial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void addWithDifferentPower() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8, 0, 12});

        assertArrayEquals(new int[] {7, 5, 14, 7, 12}, p1.add(p2).coeffs);
    }

    @Test
    void addWithNegativeItself() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {-4, -3, -6, -7});

        assertEquals("0", p1.add(p2).toString());
    }

    @Test
    void addWithSamePower() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {2, 1, 8, 3});

        assertEquals("10x^3 + 14x^2 + 4x + 6", p1.add(p2).toString());
    }

    @Test
    void addWithNegativeAnswer() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {-5, -5, -8, 3});

        assertEquals("10x^3 - 2x^2 - x - 1", p1.add(p2).toString());
    }

    @Test
    void addWithZero() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {0});

        assertEquals("7x^3 + 6x^2 + 4x + 4", p1.add(p2).toString());
    }


    @Test
    void subtractWithItself() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        assertEquals("0", p1.subtract(p1).toString());
    }

    @Test
    void subtractSamePower() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {2, 13, 2, 1});
        assertEquals("6x^3 + 4x^2 - 9x + 2", p1.subtract(p2).toString());
    }

    @Test
    void subtractDiffPower() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {2, 13, 2, 1, 11});
        assertEquals("-11x^4 + 6x^3 + 4x^2 - 9x + 2", p1.subtract(p2).toString());
    }

    @Test
    void multiplyRegular() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});

        assertEquals("56x^5 + 62x^4 + 57x^3 + 56x^2 + 17x + 12", p1.multiply(p2).toString());
    }

    @Test
    void multiplyByZero() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{0});

        assertEquals("0", p1.multiply(p2).toString());
    }

    @Test
    void evaluation() {
        Polynomial p = new Polynomial(new int[] {0, 1, 1});
        assertEquals(6, p.evaluate(2));
    }

    @Test
    void evaluationWithZero() {
        Polynomial p = new Polynomial(new int[] {0, 1, 1});
        assertEquals(0, p.evaluate(0));
    }

    @Test
    void eqTestTrue() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});
        Polynomial b = new Polynomial(new int[] {1, 2, 3, 6});

        assertTrue(a.equals(b));
    }

    @Test
    void eqTestNull() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});

        assertFalse(a.equals(null));
    }

    @Test
    void eqTestSelf() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});

        assertTrue(a.equals(a));
        assertEquals(a, a);
    }

    @Test
    void eqTestFalse() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});
        Polynomial c = new Polynomial(new int[] {1, 2, 3, 8});

        assertNotEquals(a, c);
        assertFalse(a.equals(c));
    }

    @Test
    void differentiate2() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});
        assertArrayEquals(new int[] {6, 24}, a.differentiate(2).coeffs);
    }

    @Test
    void differentiate3() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});

        assertArrayEquals(new int[] {24}, a.differentiate(3).coeffs);
    }

    @Test
    void differentiateMoreThanPower() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});

        assertArrayEquals(new int[] {0}, a.differentiate(4).coeffs);
    }

    @Test
    void testPolyMake() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});
        assertArrayEquals(new int[] {1, 2, 3, 4}, a.coeffs);
        assertEquals(3, a.power);
    }

    @Test
    void testToString() {
        Polynomial a = new Polynomial(new int[] {1, -2, 3, -4});
        assertEquals("-4x^3 + 3x^2 - 2x + 1", a.toString());
    }

    @Test
    void testToStringWithZeroCoeffs() {
        Polynomial a = new Polynomial(new int[] {1, -2, 0, -4});
        assertEquals("-4x^3 - 2x + 1", a.toString());
    }

    @Test
    void testZeroToString() {
        Polynomial a = new Polynomial(new int[] {0});
        assertEquals("0", a.toString());
    }

    @Test
    void testNothingToString() {
        Polynomial a = new Polynomial(new int[] {});
        assertEquals("0", a.toString());
    }
    
    @Test
    void testFromTz() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        System.out.println(p1.add(p2.differentiate(1)).toString());
        System.out.println(p1.multiply(p2).evaluate(2));
    }

    @Test
    void testHashcodeBase() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{4, 3, 6, 7});
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testHashcodeItself() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        int h1 = p1.hashCode();
        int h2 = p1.hashCode();
        assertEquals(h1, h2);
    }

    @Test
    void testHashcodeEqual() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{4, 3, 6, 7, 0, 0});
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();
        assertEquals(h1, h2);
    }

}