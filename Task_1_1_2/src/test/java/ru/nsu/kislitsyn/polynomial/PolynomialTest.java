package ru.nsu.kislitsyn.polynomial;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PolynomialTest {

    @Test
    void addWithDifferentPower() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8, 0, 12});

        assertArrayEquals(new int[] {7, 5, 14, 7, 12}, p1.add(p2).getCoeffs());
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

        Polynomial expected = new Polynomial(new int[] {6, 4, 14, 10});

        assertEquals(expected, p1.add(p2));
    }

    @Test
    void addWithNegativeAnswer() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {-5, -5, -8, 3});
        Polynomial expected = new Polynomial(new int[] {-1, -1, -2, 10});

        assertEquals(expected, p1.add(p2));
    }

    @Test
    void addWithZero() {
        Polynomial p1 = new Polynomial(new int[] {4, 4, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {0});
        assertEquals(p1, p1.add(p2));
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
        Polynomial expected = new Polynomial(new int[] {2, -9, 4, 6});
        assertEquals(expected, p1.subtract(p2));
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

        Polynomial expected = new Polynomial(new int[] {12, 17, 56, 57, 62, 56});
        assertEquals(expected, p1.multiply(p2));
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

        assertEquals(a, b);
    }

    @Test
    void eqTestNull() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});

        assertNotEquals(a, null);
    }

    @Test
    void eqTestSelf() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});

        assertEquals(a, a);
    }

    @Test
    void eqTestFalse() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 6});
        Polynomial c = new Polynomial(new int[] {1, 2, 3, 8});

        assertNotEquals(a, c);
    }

    @Test
    void differentiate2() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});
        assertArrayEquals(new int[] {6, 24}, a.differentiate(2).getCoeffs());
    }

    @Test
    void differentiate3() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});

        assertArrayEquals(new int[] {24}, a.differentiate(3).getCoeffs());
    }

    @Test
    void differentiateMoreThanPower() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});

        assertArrayEquals(new int[] {0}, a.differentiate(4).getCoeffs());
    }

    @Test
    void testPolyMake() {
        Polynomial a = new Polynomial(new int[] {1, 2, 3, 4});
        assertArrayEquals(new int[] {1, 2, 3, 4}, a.getCoeffs());
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