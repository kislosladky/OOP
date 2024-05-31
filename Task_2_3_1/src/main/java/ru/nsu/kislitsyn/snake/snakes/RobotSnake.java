package ru.nsu.kislitsyn.snake.snakes;

import ru.nsu.kislitsyn.snake.Apples;
import ru.nsu.kislitsyn.snake.Point;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The snake that eats apples by itself.
 */
public class RobotSnake extends Snake {
    Point target = null;
//    Deque<Point> path;
    /**
     * A constructor that initializes some starting values.
     *
     * @param width - width of the field.
     * @param height - height of the field.
     * @param apples - apples.
     */
    public RobotSnake(int width, int height, Apples apples) {
        super(width, height, apples);
    }


    /**
     * Adding chooseDirection call for decision making.
     */
    @Override
    public Point moveAndEat() {
        chooseDirection(getApples());
        Point tail = move();
        if (getApples().ate(getBody())) {
            setShouldGrow(true);
        }
        return tail;
    }

    /**
     * Kind of autopilot.
     */
    private void chooseDirection(Apples apples) {
        Deque<Point> applePoints = apples.getApples();
        Deque<Point> bodyPoints = getBody();
        Point head = bodyPoints.peekFirst();

        if (target == null || !applePoints.contains(target)) {
            findTarget(applePoints, bodyPoints);
        }


        assert head != null;
        Deque<Direction> possibleDirections = fillDirections(head);

        checkDirections(head, possibleDirections);
        if (!possibleDirections.isEmpty()) {
            setDirection(possibleDirections.peekFirst());
            if (getDirection() != possibleDirections.peekFirst()
                    && possibleDirections.size() > 1) {
                setDirection(possibleDirections.peekLast());
            }
        }
    }

    /**
     * Sets the closest apple as a target for the snake.
     */
    private void findTarget(Deque<Point> apples, Deque<Point> body) {
        Point head = body.peekFirst();

        this.target = findClosestPointTo(head, apples);
    }

    /**
     * Finds the point from targets that is the closest to the source.
     */
    private Point findClosestPointTo(Point from, Deque<Point> targets) {
        int minDistance = Integer.MAX_VALUE / 2;
        Point closestTarget = null;

        for (Point target : targets) {
            int distance = distanceBetween(from,target);
            if (distance < minDistance) {
                minDistance = distance;
                closestTarget = target;
            }
        }
        return closestTarget;
    }

    /**
     * Calculates the distance between 2 points.
     */
    private int distanceBetween(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Fills the deque with potential directions to move.
     */
    private Deque<Direction> fillDirections(Point head) {
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

    /**
     * Validates directions for snake to move.
     */
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

    /**
     * A simple override to spawn tha snake in different place.
     */
    void init() {
        this.getBody().add(new Point(getWidth() - 1, getHeight() - 1));
    }
}
