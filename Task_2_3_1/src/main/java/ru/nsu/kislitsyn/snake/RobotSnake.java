package ru.nsu.kislitsyn.snake;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class RobotSnake extends Snake{
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

    //Эта змейка должна сама выбирать себе траекторию
    //Стоит значит, надо написать для нее новый метод? или сделать override move?

    @Override
    public Point moveAndEat() {
        chooseDirection(getApples());
        Point tail = move();
        if (getApples().ate(getBody())) {
            setShouldGrow(true);
        }
        return tail;
    }

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

    private void findTarget(Deque<Point> apples, Deque<Point> body) {
        Point head = body.peekFirst();

        this.target = findClosestPointTo(head, apples);
    }

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

    private int distanceBetween(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

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
//            iter.remove();
        }
    }

    void init() {
        this.getBody().add(new Point(getWidth() - 1, getHeight() - 1));
    }
}
