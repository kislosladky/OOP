package ru.nsu.kislitsyn.calculator;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
* A class implementing a calculator with some basic functions.
*/
public class Calculator {
    private final Deque<Expression> stack;
    private final Deque<Double> numbers;
    private final Scanner inputScanner;

    /**
    * Constructor of class.
    */
    public Calculator() {
        this.stack = new ArrayDeque<>();
        this.numbers = new ArrayDeque<>();
        this.inputScanner = new Scanner(System.in);
    }


    /**
    * Turns string into deque of tokens.
    *
    * @throws IOException if the input string has wrong format.
    */
    private void inputScan() throws IOException {
        String[] input = inputScanner.nextLine().split("\s", 0);
        for (String string : input) {
            try {
                stack.push(new Number(Double.parseDouble(string)));
            } catch (NumberFormatException e) {
                stack.push(findOperation(string));
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
    private Operation findOperation(String operation) throws IOException {
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
            default -> throw new IOException();
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
        while (!stack.isEmpty()) {
            //    switch (stack.pop()) {
            //        case Number num          -> numbers.push(num.number());
            //        case Operation operation -> switchOperation(operation);
            //        default                  -> throw new IOException();
            //    }
            //}
            if (stack.peek() instanceof Number) { // contains number
                numbers.push(((Number) stack.pop()).number());
            } else {
                switch ((Operation) stack.pop()) {
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

        if (numbers.size() != 1 || numbers.peek().isNaN()) {
            numbers.clear();
            throw new IOException();
        } else {
            return numbers.pop();
        }
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