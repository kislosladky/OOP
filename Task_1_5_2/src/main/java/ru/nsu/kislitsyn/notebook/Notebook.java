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

/**
 * www.
 */
public class Notebook {
    @Option(name = "-add", usage = "adding new note to notebook")
    private boolean add = false;

    @Option(name = "-rm", usage = "deleting note from notebook")
    private boolean delete = false;
    @Option(name = "-show", usage = "showing not from notebook")
    private boolean show = false;
    @Argument
    private List<String> argument = new ArrayList<>();

    /**
     * haha.
     *
     * @param add .
     */
    public void setAdd(boolean add) {
        this.add = add;
    }

    /**
     * sbb.
     *
     * @param delete .
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * wwbw.
     *
     * @param show .
     */
    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * beweh.
     *
     * @param argument .
     */
    public void setArgument(List<String> argument) {
        this.argument = argument;
    }

    /**
     * ddndh.
     *
     * @param args .
     */
    void doMain(String[] args) {
        File notebookJson = Paths.get("notebook.json").toFile();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Note> parsedJson =
                    new ArrayList<>(Arrays.asList(objectMapper
                            .readValue(notebookJson, Note[].class)));
            if (this.add) {
                Note newNote = new Note(this.argument.get(0), this.argument.get(1));
                parsedJson.add(newNote);
                objectMapper.writeValue(notebookJson, parsedJson);
            }

            if (this.delete) {
                String headerToDelete = this.argument.get(0);
                parsedJson = parsedJson.stream().filter(x -> !x.getHeader().equals(headerToDelete))
                        .collect(Collectors.toList());
                objectMapper.writeValue(notebookJson, parsedJson);
            }

            if (this.show) {
                if (!this.argument.isEmpty()) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    Date from = format.parse(this.argument.get(0));
                    this.argument.remove(0);
                    Date till = format.parse(this.argument.get(0));
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

    /**
     * enee.
     *
     * @param args .
     */
    public static void main(String[] args) {
        Notebook notebook = new Notebook();
        try {
            CmdLineParser parser = new CmdLineParser(notebook);
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
        }
        notebook.doMain(args);
    }

    /**
     * ddndh.
     */
    static class Note implements Comparable<Note> {
        private String header;
        private String body;
        private Date date;

        /**
         * heheh.
         *
         * @return .
         */
        public String getHeader() {
            return this.header;
        }

        /**
         * wheheheh.
         *
         * @param header .
         */
        public void setHeader(String header) {
            this.header = header;
        }

        /**
         * eheheh.
         *
         * @return .
         */
        public String getBody() {
            return this.body;
        }

        /**
         * eheheheh.
         *
         * @param body .
         */
        public void setBody(String body) {
            this.body = body;
        }

        /**
         * eehehh.
         *
         * @return .
         */
        public Date getDate() {
            return this.date;
        }

        /**
         * eheheheh.
         *
         * @param date .
         */
        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * default constructor.
         */
        public Note() {
        }

        /**
         * eehheh.
         *
         * @param header g.
         * @param body   wg.
         */
        public Note(String header, String body) {
            this.header = header;
            this.body = body;
            this.date = new Date();
        }

        /**
         * eehehe.
         *
         * @param note the object to be compared.
         *
         * @return eh.
         */
        public int compareTo(Note note) {
            return this.date.compareTo(note.getDate());
        }

        /**
         * ehehehe.
         *
         * @return .
         */
        public String toString() {
            return this.header + " created in " + this.date.toString() + "\n" + this.body;
        }
    }
}