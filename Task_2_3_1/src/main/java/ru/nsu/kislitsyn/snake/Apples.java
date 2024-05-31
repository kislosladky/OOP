package ru.nsu.kislitsyn.snake;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.kislitsyn.snake.snakes.Snake;


/**
 * Class of apples for snakes.
 */
@AllArgsConstructor
@Setter
public class Apples {
    @Getter
    private final Deque<Point> apples = new ArrayDeque<>();
    private final Random random = new Random();
    private int width;
    private int height;

    /**
     * Spawns an apple without collisions with snakes.
     */
    public void spawnApple(List<Deque<Point>> snakes) {
        Point apple;

        do {
            apple = new Point(Math.abs(random.nextInt()) % width,
                    Math.abs(random.nextInt()) % height);
        } while (Snake.intersectAny(apple, snakes) || Snake.intersect(apple, apples));
        apples.add(apple);
    }

    /**
     * Checks if the snake ate an apple.
     */
    public boolean ate(Deque<Point> body) {
        Point head = body.peekFirst();

        if (apples.contains(head)) {
            apples.remove(head);
            return true;
        }

        return false;
    }

    public void restart() {
        apples.clear();
    }
}

