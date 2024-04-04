package ru.nsu.kislitsyn.snake;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;


/**
 * This class contains of all snake logic.
 */
public class Snake {
    private final Random random = new Random();
    private final ArrayDeque<Point> body;
    private final Deque<Point> apples;
    private Direction direction;
    private Direction previousDirection;
    private int width;
    private int height;
    private boolean shouldGrow;
    private int level;

    /**
     * A constructor that initializes some starting values.
     */
    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        this.apples = new ArrayDeque<>();
        this.body = new ArrayDeque<>();
        init();
        this.direction = Direction.RIGHT;
        level = 1;
        shouldGrow = false;
    }


    /**
     * Setter for the height of the field.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Setter for the width of the field.
     */
    public void setWidth(int witdh) {
        this.width = witdh;
    }


    /**
     * Setter for direction that deals with a quite tricky bug.
     */
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

    /**
     * Getter for the snake body.
     */
    public ArrayDeque<Point> getBody() {
        return body;
    }

    /**
     * Getter for the list of apples.
     */
    public Deque<Point> getApples() {
        return apples;
    }

    /**
     * Sort of getter to increase the level in the game.
     */
    public void increaseLevel() {
        level++;
    }


    /**
     * This function moves the snake and eats the apple if needed.
     *
     * @return the coordinates fo tail.
     */
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

    /**
     * Initializes the starting condition of the snake.
     */
    private void init() {
        this.body.add(new Point(0, 0));
        for (int i = 0; i < level + 2; i++) {
            spawnApple();
        }
    }

    /**
     * Restarts the snake and apples.
     */
    public void restart() {
        this.body.clear();
        this.apples.clear();
        this.direction = Direction.RIGHT;
        init();
    }

    /**
     * Moves the snake into Direction.
     * The last cell of body is being removed if the snake ate an apple in the previous step.
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

    /**
     * Spawns the apple randomly without collisions.
     */
    private void spawnApple() {
        Point apple;
        do {
            apple = new Point(Math.abs(random.nextInt()) % width,
                    Math.abs(random.nextInt()) % height);
        } while (intersect(apple, body));
        System.out.println("Apple x: " + apple.x() + ", y: " + apple.y());
        apples.add(apple);
    }

    /**
     * Checks if the snake bumped into itself.
     */
    public boolean bumped() {
        Point head = body.pollFirst();
        assert head != null;
        try {
            return intersect(head, body);
        } finally {
            body.addFirst(head);
        }
    }

    /**
     * Checks for collision of point and deque of points.
     */
    private boolean intersect(Point point, Deque<Point> deque) {
        return deque.contains(point);
    }

    /**
     * Enumeration for directions where snake can move.
     */
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}