package ru.nsu.kislitsyn.substring;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
* Class to work with file and construct substring of the file.
*/
public class FileToString {
    private final static int BUFFER_SIZE = 10000;
    private BufferedReader reader;
    private int readFromFile;
    private final char[] buffer;
    private int readFromBuffer;
    private final String filename;
    private boolean inited;
    private final int lengthOfSubstring;

    /**
    * Constructor of the class.
    *
    * @param filename name of the file.
    * @param lengthOfSubstring length of the substring we are searching.
    */
    public FileToString(String filename, int lengthOfSubstring) {
        this.filename = filename;
        this.readFromBuffer = 0;
        this.readFromFile = 0;
        this.inited = false;
        this.buffer = new char[BUFFER_SIZE];
        this.lengthOfSubstring = lengthOfSubstring;
    }

    /**
    * Function that updates the input string by removing the
    * first character and adding new one in the end.
    *
    * @param string string we need to update using file.
    *
    * @return true if the string was updated, otherwise false.
    *
    * @throws IOException if the file was not found.
    */
    public boolean updateString(StringBuilder string) throws IOException {
        if (!inited) {
            inited = true;
            Path path = Paths.get(filename);
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            readFromFile = reader.read(buffer, 0, BUFFER_SIZE);
            if (readFromFile == -1) {
                reader.close();
                return false;
            }
            string.append(buffer, 0, lengthOfSubstring);
            readFromBuffer += lengthOfSubstring;
            return true;
        } else {
            if (readFromBuffer < readFromFile) {
                string.deleteCharAt(0);
                string.append(buffer[readFromBuffer++]);
                return true;
            } else {
                readFromFile = reader.read(buffer, 0, BUFFER_SIZE);
                if (readFromFile == -1) {
                    reader.close();
                    return false;
                }
                readFromBuffer = 0;
                string.deleteCharAt(0);
                string.append(buffer[readFromBuffer++]);
                return true;
            }
        }
    }
}
