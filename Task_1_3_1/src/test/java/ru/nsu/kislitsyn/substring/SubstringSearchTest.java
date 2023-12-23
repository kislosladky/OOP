package ru.nsu.kislitsyn.substring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SubstringSearchTest {
    @Test
    void tzTest() {
        SubstringSearch search = new SubstringSearch("bra", "input.txt");
        search.rabinKarp();
        List<Long> expected = new ArrayList<>();
        expected.add(1L);
        expected.add(8L);
        assertEquals(expected, search.getAnswer());
    }

    @Test
    void hebrewTextTest() {
        SubstringSearch search = new SubstringSearch("ש", "hebrew.txt");
        search.rabinKarp();
        List<Long> expected = new ArrayList<>();
        expected.add(22L);
        expected.add(26L);
        expected.add(54L);
        assertEquals(expected, search.getAnswer().subList(0,3));
    }

    @Test
    void arabicTextTest() {
        SubstringSearch search = new SubstringSearch("ق", "arabic.txt");
        search.rabinKarp();
        List<Long> expected = new ArrayList<>();
        expected.add(6L);
        expected.add(12L);
        expected.add(18L);
        assertEquals(expected, search.getAnswer().subList(0,3));
    }

    @Test
    void largeFile() {
        String fileName = "large.txt";
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            for (int j = 0; j < 15; j++) {
                for (int i = 0; i < 100_000_000; i++) {
                    writer.write("aaaaaaaaaa");
                }
                writer.write("bruh");
            }
        } catch (IOException e) {
            return;
        }

        SubstringSearch search = new SubstringSearch("bru", "large.txt");
        search.rabinKarp();
        File file = new File("large.txt");
        file.delete();
        List<Long> expected = new ArrayList<>();
        expected.add(1_000_000_000L);
        expected.add(2_000_000_004L);
        assertEquals(expected, search.getAnswer().subList(0,3));
    }

    @Test
    void noFileTest() {

        SubstringSearch search = new SubstringSearch("Aa", "file.txt");
        search.rabinKarp();
        assertEquals(0, search.getAnswer().size());
    }

    @Test
    void wordBetweenBatchesTest() {
        SubstringSearch search = new SubstringSearch("capy", "10k.txt");
        search.rabinKarp();
        assertEquals(9994, search.getAnswer().get(0));
    }
}