package ru.nsu.kislitsyn.calculator;

/**
* An exception that indicates the end of work.
*/
public class FinishException extends RuntimeException {
    public FinishException(String message) {
        super(message);
    }
}
