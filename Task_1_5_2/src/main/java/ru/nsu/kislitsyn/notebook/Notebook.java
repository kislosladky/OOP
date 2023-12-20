package ru.nsu.kislitsyn.notebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Notebook {
    @Option(name = "-add", usage = "adding new note to notebook")
    private boolean add;

    @Option(name = "-rm", usage = "deleting note from notebook")
    private boolean delete;
    @Option(name = "-show", usage = "showing not from notebook")
    private boolean show;
    @Argument
    private List<String> argument = new ArrayList<>();

    private void doMain(String[] args) {
        try {
            CmdLineParser parser = new CmdLineParser(this);
            parser.parseArgument(args);
        } catch (CmdLineException var10) {
            System.out.println(var10.getMessage());
        }

        File notebookJson = Paths.get("notebook.json").toFile();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Note> parsedJson = new ArrayList<>(Arrays.asList(objectMapper.readValue(notebookJson, Note[].class)));
            if (this.add) {
                Note newNote = new Note(this.argument.get(0), this.argument.get(1));
                parsedJson.add(newNote);
                objectMapper.writeValue(notebookJson, parsedJson);
            }

            if (this.delete) {
                String headerToDelete = this.argument.get(0);
                parsedJson = parsedJson.stream().filter(x -> x.getHeader().equals(headerToDelete)
                ).collect(Collectors.toList());
                objectMapper.writeValue(notebookJson, parsedJson);
            }

            if (this.show) {
                if (!this.argument.isEmpty()) {
                    System.out.println("Filtering");
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    Date from = format.parse(this.argument.get(0));
                    Date till = format.parse(this.argument.get(1));
                    this.argument.remove(0);
                    this.argument.remove(0);
                    this.argument = this.argument.stream().map(String::toLowerCase).toList();
                    parsedJson = new ArrayList<>(parsedJson.stream()
                            .filter(note -> note.getDate().compareTo(from) >= 0
                                    && note.getDate().compareTo(till) <= 0)
                            .filter(note -> !Arrays.stream(note.getHeader().split(" "))
                                    .map(String::toLowerCase)
                                    .filter((word) -> this.argument.contains(word))
                                    .toList().isEmpty())
                            .toList());
                }

                Collections.sort(parsedJson);
                System.out.println("answer");
                parsedJson.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.out.println("Unable to parse the json");
            System.err.println(e.getMessage());
        } catch (ParseException parseException) {
            System.out.println("Unable to parse the date");
            System.err.println(parseException.getMessage());
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
            return this.header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getBody() {
            return this.body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Date getDate() {
            return this.date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Note() {}

        public Note(String header, String body) {
            this.header = header;
            this.body = body;
            this.date = new Date();
        }

        public int compareTo(Note note) {
            return this.date.compareTo(note.getDate());
        }

        public String toString() {
            return this.header + " created in " + this.date.toString() + "\n" + this.body;
        }
    }
}