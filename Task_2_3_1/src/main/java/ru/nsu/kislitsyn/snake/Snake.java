package ru.nsu.kislitsyn.snake;

import java.util.*;

import javafx.application.Application;
//import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Snake {
    private final Random random = new Random();
    private final ArrayDeque<Point> body;
    private final Deque<Point> apples;
    private Direction direction;
    private int step;
    private int size;
    private boolean shouldGrow;

    public Snake(int step) {
        this.step = step;
        this.apples = new ArrayDeque<>();
        this.body = new ArrayDeque<>();
        this.body.add(new Point(0, 0));
        this.body.add(new Point(0, step));
        this.body.add(new Point(0, 2 * step));
        this.direction = Direction.RIGHT;
        size = 10 * step;
        shouldGrow = false;
        spawnApple();
    }
    public double getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public double getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ArrayDeque<Point> getBody() {
        return body;
    }
    public Point moveAndEat() throws BumpedException {
        Point tail = move();
        Point head = body.peekFirst();
        if (bumped()) {
            throw new BumpedException("Bumped");
        }
        if (intersect(head, apples)) {
            apples.remove(head);
            spawnApple();
            shouldGrow = true;
        }

        return tail;
    }

    /**
     * Moves the snake into Direction.
     *
     * @return the head of the snake.
     */
    private Point move() {
        Point head = body.peekFirst();
        Point newHead;
        switch (direction) {
            case UP: {
                newHead = new Point(head.x(), (head.y() + step) % size);
                break;
            }
            case DOWN: {
                newHead = new Point(head.x(), (head.y() - step) % size);

                break;
            }
            case LEFT: {
                newHead = new Point((head.x() - step) % size, head.y());

                break;
            }
            case RIGHT: {
//                int y = head.y() - step > 0 ? (head.y() - step) % size : step * 9;
                newHead = new Point((head.x() + step) % size, head.y());
                break;
            }
            default: throw new IllegalStateException();
        }
        body.addFirst(newHead);

        if (shouldGrow) {
            shouldGrow = false;
            return body.peekLast();
        } else {
            return body.pollLast();
        }

    }

    private void spawnApple() {
        Point apple;
        do {
            apple = new Point(random.nextInt() % size, random.nextInt() % size);
        } while (intersect(apple, body));

        apples.add(apple);
//        return apple;
    }

    private boolean bumped() {
        Point head = body.pollFirst();
        assert head != null;
        try {
            return intersect(head, body);
        } finally {
            body.addFirst(head);
        }
    }
    private boolean intersect(Point point, Deque<Point> deque) {
        return deque.contains(point);
    }
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}