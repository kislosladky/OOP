package ru.nsu.kislitsyn.studentsbook;

/**
* Enum for mark for subject.
*/
public enum Mark {
    PASS(-1),
    FAIL(2),
    EXCELLENT(5),
    GOOD(4),
    ACCEPTABLE(3),
    UNACCEPTABLE(2),
    NOT_STATED(0);
    private final int markValue;

    Mark(int mark) {
        this.markValue = mark;
    }

    public int getMark() {
        return markValue;
    }

}