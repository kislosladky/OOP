package ru.nsu.kislitsyn.snake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.kislitsyn.snake.snakes.Snake;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Setter
public class Apples {
    @Getter
    private final Deque<Point> apples = new ArrayDeque<>();
    private final Random random = new Random();
    private int width;
    private int height;

    public void spawnApple(List<Deque<Point>> snakes) {
        Point apple;

        do {
            apple = new Point(Math.abs(random.nextInt()) % width,
                    Math.abs(random.nextInt()) % height);
//            Point finalApple = apple;
        } while (Snake.intersectAny(apple, snakes) || Snake.intersect(apple, apples));
        apples.add(apple);
    }

    public boolean ate(Deque<Point> body) {
        Point head = body.peekFirst();

        if (apples.contains(head)) {
            apples.remove(head);
//            spawnApple(body);
            return true;
        }

        return false;
    }

    public void restart() {
        apples.clear();
    }
}

