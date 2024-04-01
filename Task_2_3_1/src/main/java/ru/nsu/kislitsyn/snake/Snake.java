package ru.nsu.kislitsyn.snake;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;


public class Snake {
    private final Random random = new Random();
    private final ArrayDeque<Point> body;
    private final Deque<Point> apples;
    private Direction direction;
    private Direction previousDirection;
    private int width;
    private int height;
    private boolean shouldGrow;

    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        this.apples = new ArrayDeque<>();
        this.body = new ArrayDeque<>();
        init();
        this.direction = Direction.RIGHT;

        shouldGrow = false;

    }


    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int witdh) {
        this.width = witdh;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        switch (direction) {
            case UP: {
                if (this.previousDirection != Direction.DOWN) {
                    this.direction = direction;
                }
                break;
            }
            case DOWN: {
                if (this.previousDirection != Direction.UP) {
                    this.direction = direction;
                }
                break;
            }
            case LEFT: {
                if (this.previousDirection != Direction.RIGHT) {
                    this.direction = direction;
                }
                break;
            }
            case RIGHT: {
                if (this.previousDirection != Direction.LEFT) {
                    this.direction = direction;
                }
                break;
            }
            default:
                throw new IllegalStateException();
        }
    }

    public ArrayDeque<Point> getBody() {
        return body;
    }

    public Deque<Point> getApples() {
        return apples;
    }

    public Point moveAndEat() {
        Point tail = move();
        Point head = body.peekFirst();

        if (intersect(head, apples)) {
            apples.remove(head);
            spawnApple();
            shouldGrow = true;
        }

        return tail;
    }

    private void init() {
        this.body.add(new Point(0, 0));
        spawnApple();
        spawnApple();
    }

    public void restart() {
        this.body.clear();
        this.apples.clear();
        init();
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
                previousDirection = Direction.UP;
                newHead = new Point(head.x(), head.y() == 0 ? height - 1 : head.y() - 1);
                break;
            }
            case DOWN: {
                previousDirection = Direction.DOWN;
                newHead = new Point(head.x(), (head.y() + 1) % height);
                break;
            }
            case LEFT: {
                previousDirection = Direction.LEFT;
                newHead = new Point(head.x() == 0 ? width - 1 : head.x() - 1, head.y());
                break;
            }
            case RIGHT: {
                previousDirection = Direction.RIGHT;
                newHead = new Point((head.x() + 1) % width, head.y());
                break;
            }
            default:
                throw new IllegalStateException();
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
            apple = new Point(Math.abs(random.nextInt()) % width, Math.abs(random.nextInt()) % height);
        } while (intersect(apple, body));
        System.out.println("Apple x: " + apple.x() + ", y: " + apple.y());
        apples.add(apple);
    }

    public boolean bumped() {
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