package ru.nsu.kislitsyn.substring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
* Class to work with file and construct substring of the file.
*/
public class FileToString {
    private static final int BUFFER_SIZE = 10000;
    private BufferedReader reader;
    private int readFromFile;
    private final char[] buffer;
    private int readFromBuffer;
    private final String filename;
    private final int lengthOfSubstring;

    /**
    * Constructor of the class.
    *
    * @param filename          name of the file.
    * @param lengthOfSubstring length of the substring we are searching.
    */
    public FileToString(String filename, int lengthOfSubstring) {
        this.filename = filename;
        this.readFromBuffer = 0;
        this.readFromFile = 0;
        this.buffer = new char[BUFFER_SIZE];
        this.lengthOfSubstring = lengthOfSubstring;
    }


    /**
    * Function that initializes string of the same length as substring I am searching for.
    *
    * @param string stringBuilder where I store the string.
    *
    * @return true if string is inited, otherwise false.
    *
    * @throws IOException if file was not opened.
    */

    public boolean initializeString(StringBuilder string) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            inputStream = new FileInputStream(filename);
        }
        reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        readFromFile = reader.read(buffer, 0, BUFFER_SIZE);
        if (readFromFile == -1) {
            reader.close();
            return false;
        }
        string.append(buffer, 0, lengthOfSubstring);
        readFromBuffer += lengthOfSubstring;
        return true;
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
