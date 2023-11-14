package ru.nsu.kislitsyn.substring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* Class that searches substring using Rabin Carp algorithm.
*/
public class SubstringSearch {
    private final String substring;
    private final List<Long> answer;
    private final long PRIME_NUMBER = 17;
    private final FileToString fileToString;

    /**
    * Getter for answer arrayList.
    *
    * @return answer arrayList.
    */
    public List<Long> getAnswer() {
        return answer;
    }


    /**
    * My hash function.
    *
    * @param string string we need to hash.
    *
    * @return hash.
    */
    private long myHash(String string) {
        long answ = 0;
        long power = PRIME_NUMBER;
        for (int i = 0; i < string.length(); i++) {
            answ += power * string.charAt(i);
            power *= PRIME_NUMBER;
        }
        return answ;
    }

    /**
    * Constructor of class.
    *
    * @param substring substring we need to search.
    * @param filename  file where we need to search the substring.
    */
    public SubstringSearch(String substring, String filename) {
        this.substring = substring;
        this.answer = new ArrayList<>();
        this.fileToString = new FileToString(filename, substring.length());
    }

    /**
    * Function that finds the substring.
    */
    public void rabinKarp() {
        StringBuilder string = new StringBuilder();

        try {
            fileToString.initializeString(string);
        } catch (IOException e) {
            System.err.println("Unable to read file");
            return;
        }
        long hashSubStr = myHash(substring);
        long hashInpStr = myHash(String.valueOf(string));
        char firstChar;
        long powerOfPrime = (long) Math.pow(PRIME_NUMBER, substring.length());
        long i = 0;

        firstChar = string.charAt(0);
        if (hashInpStr == hashSubStr) {
            answer.add(i);
        }
        try {
            while (fileToString.updateString(string)) {
                hashInpStr = ((hashInpStr
                        - (PRIME_NUMBER * firstChar)) / PRIME_NUMBER)
                        + powerOfPrime * string.charAt(string.length() - 1);
                i++;
                firstChar = string.charAt(0);
                if (hashInpStr == hashSubStr) {
                    answer.add(i);
                }
            }
        } catch (IOException e) {
            System.err.println("Unable to read file");
        }
    }
}