package ru.nsu.kislitsyn.calculator;

/**
* Enum for all the operations of calculator.
*/
public enum Operation implements Expression {
    ADD(2),
    SUBTRACT(2),
    DIVIDE(2),
    MULTIPLY(2),
    LOG(1),
    POWER(2),
    SQRT(1),
    SIN(1),
    COS(1),
    MEOW(0),
    NONFORMAT(0);

    private final int arity;

    Operation(int arity) {
        this.arity = arity;
    }
}
