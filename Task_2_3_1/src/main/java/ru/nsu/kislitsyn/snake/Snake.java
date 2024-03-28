package ru.nsu.kislitsyn.snake;

import java.util.*;


//TODO переделать на сетку
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
        this.body.add(new Point(0, 1));
        this.body.add(new Point(0, 2));
        this.direction = Direction.RIGHT;
        size = 10;
        shouldGrow = false;
        spawnApple();
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
        switch (direction) {
            case UP: {
                if (this.direction != Direction.DOWN) {
                    this.direction = direction;
                }
                break;
            }
            case DOWN: {
                if (this.direction != Direction.UP) {
                    this.direction = direction;
                }
                break;
            }
            case LEFT: {
                if (this.direction != Direction.RIGHT) {
                    this.direction = direction;
                }
                break;
            }
            case RIGHT: {
                if (this.direction != Direction.LEFT) {
                    this.direction = direction;
                }
                break;
            }
            default: throw new IllegalStateException();
        }
    }

    public ArrayDeque<Point> getBody() {
        return body;
    }

    public Deque<Point> getApples() {
        return apples;
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
//            tail = null;
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
                newHead = new Point(head.x(), head.y() == 0 ? 9 : head.y() - 1);
                break;
            }
            case DOWN: {
                newHead = new Point(head.x(), (head.y() + 1) % size);
                break;
            }
            case LEFT: {
                newHead = new Point(head.x() == 0 ? 9 : head.x() - 1, head.y());

                break;
            }
            case RIGHT: {
//                int y = head.y() - step > 0 ? (head.y() - step) % size : step * 9;
                newHead = new Point((head.x() + 1) % size, head.y());
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
            apple = new Point(Math.abs(random.nextInt()) % 10, Math.abs(random.nextInt()) % 10);
        } while (intersect(apple, body));
        System.out.println("Apple x: " + apple.x() + ", y: " + apple.y());
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