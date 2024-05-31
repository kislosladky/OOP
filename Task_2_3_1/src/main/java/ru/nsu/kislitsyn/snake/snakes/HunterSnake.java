package ru.nsu.kislitsyn.snake.snakes;

import ru.nsu.kislitsyn.snake.Apples;
import ru.nsu.kislitsyn.snake.Point;
import ru.nsu.kislitsyn.snake.scenemakers.SnakeSceneMaker;

import java.util.ArrayDeque;
import java.util.Deque;

public class HunterSnake extends Snake {
    private final Deque<Point> playerSnake;
    /**
     * A constructor that initializes some starting values.
     *
     * @param width
     * @param height
     * @param apples
     */
    public HunterSnake(int width, int height, Apples apples, Deque<Point> playerSnake) {
        super(width, height, apples);
        this.playerSnake = playerSnake;
    }

    @Override
    public Point moveAndEat() {
        chooseDirection();
        Point tail = move();
        if (getApples().ate(getBody())) {
            setShouldGrow(true);
        }
        return tail;
    }

    private void chooseDirection() {
        Point target = playerSnake.peekFirst();
        Point head = getBody().peekFirst();
        assert head != null;
        assert target != null;
        Deque<Direction> possibleDirections = fillDirections(head, target);

        checkDirections(head, possibleDirections);

        if (!possibleDirections.isEmpty()) {
            setDirection(possibleDirections.peekFirst());
            if (getDirection() != possibleDirections.peekFirst()
                    && possibleDirections.size() > 1) {
                setDirection(possibleDirections.peekLast());
            }
        }
    }

    private Deque<Direction> fillDirections(Point head, Point target) {
        Deque<Direction> possibleDirections = new ArrayDeque<>();

        assert head != null;
        if (head.x() < target.x()) {
            possibleDirections.add(Direction.RIGHT);
        } else if (head.x() > target.x()) {
            possibleDirections.add(Direction.LEFT);
        }

        if (head.y() < target.y()) {
            possibleDirections.add(Direction.DOWN);
        } else if (head.y() > target.y()) {
            possibleDirections.add(Direction.UP);
        }

        return possibleDirections;
    }

    private void checkDirections(Point head, Deque<Direction> directions) {
        for (Direction currDirection : directions) {
            switch (currDirection) {
                case UP: {
                    if (getBody().contains(new Point(head.x(), head.y() - 1))) {
                        directions.remove(currDirection);
                    }
                    break;
                }
                case DOWN: {
                    if (getBody().contains(new Point(head.x(), head.y() + 1))) {
                        directions.remove(currDirection);
                    }
                    break;
                }
                case LEFT: {
                    if (getBody().contains(new Point(head.x() - 1, head.y()))) {
                        directions.remove(currDirection);
                    }
                    break;
                }
                case RIGHT: {
                    if (getBody().contains(new Point(head.x() + 1, head.y()))) {
                        directions.remove(currDirection);
                    }
                    break;
                }
            }
        }
    }

    @Override
    void init() {
        this.getBody().add(new Point(getWidth() / 2, getHeight() / 2));
    }
}
