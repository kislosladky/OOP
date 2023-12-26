package ru.nsu.kislitsyn.notebook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;


class NotebookTest {
    @Test
    void addTest() throws IOException {
        Notebook notebook = new Notebook();
        notebook.setAdd(true);
        List<String> args = new ArrayList<>();
        args.add("test note");
        args.add("test body");
        notebook.setArgument(args);
        notebook.doMain(null);

        File notebookJson = Paths.get("notebook.json").toFile();
        ObjectMapper mapper = new ObjectMapper();

        List<Notebook.Note> parsedJson =
                new ArrayList<>(Arrays.asList(mapper
                        .readValue(notebookJson, Notebook.Note[].class)));
        parsedJson = parsedJson.stream()
                .filter(note -> note.getHeader().equals("test note")).toList();
        assertEquals("test body", parsedJson.get(0).getBody());
    }

    @Test
    void rmTest() throws IOException {
        Notebook notebook = new Notebook();
        notebook.setDelete(true);
        List<String> args = new ArrayList<>();
        args.add("test note");
        notebook.setArgument(args);
        notebook.doMain(null);

        File notebookJson = Paths.get("notebook.json").toFile();
        ObjectMapper mapper = new ObjectMapper();

        List<Notebook.Note> parsedJson =
                new ArrayList<>(Arrays.asList(mapper
                        .readValue(notebookJson, Notebook.Note[].class)));
        parsedJson = parsedJson.stream()
                .filter(note -> note.getHeader().equals("test note")).toList();
        parsedJson.forEach(System.out::println);
        assertTrue(parsedJson.isEmpty());
    }

    @Test
    void showWithBordersTest() throws InterruptedException, ParseException {
        Date from = new Date();
        Notebook notebook = new Notebook();
        notebook.setAdd(true);
        List<String> args = new ArrayList<>();
        args.add("test note");
        args.add("test body");
        notebook.setArgument(args);
        notebook.doMain(null);


        OutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Notebook notebook1 = new Notebook();
        notebook1.setShow(true);
        List<String> args1 = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        args1.add(format.format(from));
        args1.add("12.02.2041 16:42");
        args1.add("test");
        notebook1.setArgument(args1);
        notebook1.doMain(null);


        String expected = "test note created in Tue Dec 26 03:24:23 NOVT 2023\ntest body";
        assertEquals(expected.substring(0, 17), output.toString().trim().substring(0, 17));

        Notebook deleter = new Notebook();
        deleter.setDelete(true);
        List<String> deleteArgs = new ArrayList<>();
        deleteArgs.add("test note");
        deleter.setArgument(deleteArgs);
        deleter.doMain(null);
    }

    @Test
    void showTest() {
        OutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Notebook notebook = new Notebook();
        notebook.setShow(true);
        List<String> args = new ArrayList<>();
        notebook.setArgument(args);
        notebook.doMain(null);

        String expected = "Never delete this created in Tue Dec 26 11:02:25 NOVT 2023\n" +
                "Never never never";
        assertEquals(expected.substring(0, 17), output.toString().trim().substring(0, 17));
    }
}