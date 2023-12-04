package ru.nsu.kislitsyn.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;


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
    void nanTest() {
        final ByteArrayInputStream bais = new ByteArrayInputStream("log -1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("NaN\nThe end", output.toString().trim());
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
    void wikiTest() {
        final ByteArrayInputStream bais =
                new ByteArrayInputStream("- * / 15 - 7 + 1 1 3 + 2 + 1 1\nmeow".getBytes());
        System.setIn(bais);
        String[] args = null;
        Calculator.main(args);
        assertEquals("5.0\nThe end", output.toString().trim());
    }
}