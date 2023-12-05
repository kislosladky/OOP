package ru.nsu.kislitsyn.calculator;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
* A class implementing a calculator with some basic functions.
*/
public class Calculator {
    private final Stack<StackCell> stack;
    private final Stack<Double> numbers;
    private final Scanner inputScanner;

    /**
    * A record class to store either a number or an operation.
    *
    * @param number number to store.
    * @param operation operation to store.
    */
    private record StackCell(Double number, Operation operation) {
    }

    /**
    * Constructor of class.
    */
    public Calculator() {
        this.stack = new Stack<>();
        this.numbers = new Stack<>();
        this.inputScanner = new Scanner(System.in);
    }

    /**
    * A function that parses the input string into tokens.
    */
    private void inputScan() {
        String[] input = inputScanner.nextLine().split("\s", 0);
        for (String string : input) {
            try {
                stack.push(new StackCell(Double.parseDouble(string), null));
            } catch (NumberFormatException e) {
                stack.push(new StackCell(null, findOperation(string)));
            }
        }
    }

    /**
    * A function that turns string with operation name into enum.
    *
    * @param operation string that contains the name of operation.
    *
    * @return enum of operation.
    */
    private Operation findOperation(String operation) {
        return switch (operation.toLowerCase()) {
            case "/" -> Operation.DIVIDE;
            case "*" -> Operation.MULTIPLY;
            case "+" -> Operation.ADD;
            case "-" -> Operation.SUBTRACT;
            case "sin" -> Operation.SIN;
            case "cos" -> Operation.COS;
            case "pow" -> Operation.POWER;
            case "log" -> Operation.LOG;
            case "sqrt" -> Operation.SQRT;
            case "meow" -> Operation.MEOW;
            default -> Operation.NONFORMAT;
        };
    }

    /**
    * A function that calculates the input expression.
    *
    * @return the result.
    *
    * @throws FinishException is thrown if it is the end of work.
    * @throws IOException is thrown if format of expression is wrong.
    */
    private double calculate() throws FinishException, IOException {
        while (!stack.empty()) {
            if (stack.peek().operation == null) { // contains number
                numbers.push(stack.pop().number());
            } else {
                switch (stack.pop().operation()) {
                    case ADD -> numbers.push(numbers.pop() + numbers.pop());
                    case SUBTRACT -> numbers.push(numbers.pop() - numbers.pop());
                    case MULTIPLY -> numbers.push(numbers.pop() * numbers.pop());
                    case DIVIDE -> numbers.push(numbers.pop() / numbers.pop());
                    case POWER -> numbers.push(Math.pow(numbers.pop(), numbers.pop()));
                    case SQRT -> numbers.push(Math.sqrt(numbers.pop()));
                    case SIN -> numbers.push((Math.sin(numbers.pop())));
                    case COS -> numbers.push((Math.cos(numbers.pop())));
                    case LOG -> numbers.push((Math.log(numbers.pop())));
                    case MEOW -> throw new FinishException("The end");
                    default -> throw new IOException();
                }
            }
        }

        if (numbers.size() != 1) {
            numbers.clear();
            throw new IOException();
        }

        return numbers.pop();
    }

    /**
    * The main function that reads strings until meats terminating one.
    *
    * @param args args that are useless here.
    */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        while (true) {
            try {
                calculator.inputScan();
                System.out.println(calculator.calculate());
            } catch (FinishException e) {
                System.out.println("The end");
                break;
            } catch (IOException e) {
                System.out.println("The format is wrong");
            }
        }
    }
}