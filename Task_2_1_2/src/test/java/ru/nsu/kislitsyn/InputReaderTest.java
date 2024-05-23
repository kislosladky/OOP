package ru.nsu.kislitsyn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;


class InputReaderTest {
    @Test
    void readTest() throws IOException {
        List<Integer> list = InputReader.getNumbers(Files.newInputStream(Path.of("test.txt")));
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(expected, list);
    }
}