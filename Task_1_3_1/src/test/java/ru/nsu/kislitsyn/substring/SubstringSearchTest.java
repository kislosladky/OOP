package ru.nsu.kislitsyn.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


class SubstringSearchTest {
    @Test
    void tzTest() {
        SubstringSearch search = new SubstringSearch("bra", "src/main/resources/input.txt");
        search.rabinKarp();
        assertEquals(1, search.getAnswer().get(0));
        assertEquals(8, search.getAnswer().get(1));
    }

    @Test
    void hebrewTextTest() {
        SubstringSearch search = new SubstringSearch("ש", "src/main/resources/hebrew.txt");
        search.rabinKarp();
        assertEquals(22, search.getAnswer().get(0));
        assertEquals(26, search.getAnswer().get(1));
        assertEquals(329, search.getAnswer().get(16));
    }

    @Test
    void arabicTextTest() {
        SubstringSearch search = new SubstringSearch("ق", "src/main/resources/arabic.txt");
        search.rabinKarp();
        assertEquals(6, search.getAnswer().get(0));
        assertEquals(12, search.getAnswer().get(1));
        assertEquals(122, search.getAnswer().get(16));
    }

    @Test
    void largeFile() {
        String fileName = "large.txt";
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 100000000; i++) {
                    writer.write("aaaaaaaaaa");
                }
                writer.write("bruh");
            }
        } catch (IOException e) {
            return;
        }

        SubstringSearch search = new SubstringSearch("bru", "large.txt");
        search.rabinKarp();
        assertEquals(1000000000, search.getAnswer().get(0));
        assertEquals(2000000004, search.getAnswer().get(1));

        File file = new File("large.txt");
        file.delete();
    }

}