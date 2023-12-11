package ru.nsu.kislitsyn.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CalculatorTest {
    OutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(output));
    }

    @AfterAll
    public static void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void taskTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("sin + - 1 2 1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("0.0\nThe end", output.toString().trim());
    }

    @Test
    void simpleTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("+ 1 1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("2.0\nThe end", output.toString().trim());
    }

    @Test
    void simpleDoubleTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("+ 1.002 0.998\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("2.0\nThe end", output.toString().trim());
    }

    @Test
    void nanTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("log -1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("The format is wrong\nThe end", output.toString().trim());
    }

    @Test
    void infinityTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("/ 12 0\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("Infinity\nThe end", output.toString().trim());
    }

    @Test
    void negativeInfinityTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("/ -12 0\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("-Infinity\nThe end", output.toString().trim());
    }

    @Test
    void divideTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("/ -12 0.5\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("-24.0\nThe end", output.toString().trim());
    }

    @Test
    void powerTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("pow 3 2\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("9.0\nThe end", output.toString().trim());
    }

    @Test
    void powerFloatTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("pow 9 0.5\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("3.0\nThe end", output.toString().trim());
    }

    @Test
    void cosTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("cos 0.414\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("0.9155190527996969\nThe end", output.toString().trim());
    }

    @Test
    void sqrtTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("sqrt 9\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("3.0\nThe end", output.toString().trim());
    }

    @Test
    void wrongFormatTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("skgj\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("The format is wrong\nThe end", output.toString().trim());
    }

    @Test
    void wrongFormatComplicatedTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("sin 2 1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("The format is wrong\nThe end", output.toString().trim());
    }

    @Test
    void wikiTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("- * / 15 - 7 + 1 1 3 + 2 + 1 1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("5.0\nThe end", output.toString().trim());
    }
}