package ru.nsu.kislitsyn.notebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Notebook {
    @Option(name="-add", usage = "adding new note to notebook")
    private boolean add;

    @Option(name="-rm", usage = "deleting note from notebook")
    private boolean delete;

    @Option(name="-show", usage = "showing not from notebook")
    private boolean show;

    @Argument
    private List<String> argument;

    public Notebook() {
        argument = new ArrayList<>();
    }

    private void doMain(String[] args) {
        try {
            CmdLineParser parser = new CmdLineParser(this);
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
        }

        File notebookJson = Paths.get("notebook.json").toFile();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Note> parsedJson =
                    new ArrayList<>(Arrays.asList(objectMapper.readValue(notebookJson, Note[].class)));

            if (add) {
                //add
                Note newNote = new Note(argument.get(0), argument.get(1));
                parsedJson.add(newNote);
                objectMapper.writeValue(notebookJson, parsedJson);
            }

            if (delete) {
                //удаляем запись по заголовку
                String headerToDelete = argument.get(0);
                parsedJson = parsedJson.stream()
                        .filter(x -> !x.getHeader().equals(headerToDelete))
                        .collect(Collectors.toList());
                objectMapper.writeValue(notebookJson, parsedJson);
            }

            if (show) {
                //если нет аргументов, то сортируем заметки по времени и выводим
                if (!argument.isEmpty()) {
                    System.out.println("Filtering");
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    Date from = format.parse(argument.get(0));
                    Date till = format.parse(argument.get(1));
                    argument.remove(0);
                    argument.remove(0);
                    argument = argument.stream().map(String::toLowerCase).toList();
                    parsedJson = new ArrayList<>(parsedJson.stream()
                            .filter(note -> note.getDate().compareTo(from) >= 0
                                    && note.getDate().compareTo(till) <= 0)
                            .filter(note -> !Arrays.stream(note.getHeader().split("\s"))
                                    .map(String::toLowerCase)
                                    .filter(word -> argument.contains(word))
                                    .toList().isEmpty())
                            .toList());
                }
                Collections.sort(parsedJson);
                System.out.println("answer");
                parsedJson.forEach(note -> System.out.println(note.toString()));

                //если аргументы есть, то сортируем и выводим только те, где время
                //создания находится в диапазоне и заголовок содержит ключевые слова
            }
        } catch (IOException e) {
            System.out.println("Unable to parse the json");
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println("Unable to parse the date");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Notebook().doMain(args);
    }

    static class Note implements Comparable<Note> {
        private String header;
        private String body;
        private Date date;

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Note(String header, String body) {
            this.header = header;
            this.body = body;
            this.date = new Date();
        }

        public Note() {}

        @Override
        public int compareTo(Note note) {
            return this.date.compareTo(note.getDate());
        }

        @Override
        public String toString() {
            return header + " created at " + date.toString() + "\n" + body;
        }
    }
}