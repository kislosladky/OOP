package ru.nsu.kislitsyn.substring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SubstringSearch {
    private String substring;
    private final ArrayList<Long> answer;
    private final long PRIME_NUMBER = 17;
    private long myHash(String string) {
        long answ = 0;
        long power = PRIME_NUMBER;
        for (int i = 0; i < string.length(); i++) {
            answ += power * string.charAt(i);
            power *= PRIME_NUMBER;
        }
        return answ;
    }

    public SubstringSearch(String substring) {
        this.substring = substring;
        this.answer = new ArrayList<>();
    }
    private void arrayShift(char[] str) {
        for (int i = 1; i < str.length; i++) {
            str[i - 1] = str[i];
        }
    }
    public void rabinKarp(String filename) throws IOException {
        int read = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            char[] string = new char[substring.length()];
            int readCnt = reader.read(string);
            if (readCnt < substring.length()) {
                return;
            }

            long hashSubStr = myHash(substring);
            long hashInpStr = myHash(String.valueOf(string));
            long powerOfPrime = (long) Math.pow(PRIME_NUMBER, substring.length());
            long i = 0;
            char first_char;
            do {
                if (hashInpStr == hashSubStr) {
                    answer.add(i);
                }
                first_char = string[0];
                arrayShift(string);
                //TODO переделать на считывание батчами
                read = reader.read(string, string.length - 1, 1);
                hashInpStr = ((hashInpStr
                        - (PRIME_NUMBER * first_char)) / PRIME_NUMBER)
                        + powerOfPrime * string[string.length - 1];
                i++;
            } while (read > 0);
        }
    }

    public static void main(String[] args) throws IOException {
        SubstringSearch search = new SubstringSearch("bra");
        search.rabinKarp("src/main/resources/input.txt");
        for (Long answ : search.answer) {
            System.out.println(answ);
        }
    }
}