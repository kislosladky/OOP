package ru.nsu.kislitsyn.snake;

public class BumpedException extends Exception {
    public BumpedException(String errorMessage) {
        super(errorMessage);
    }
}