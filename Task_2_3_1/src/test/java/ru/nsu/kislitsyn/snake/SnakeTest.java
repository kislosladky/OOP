package ru.nsu.kislitsyn.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    @Test
    void setDirectionTest() {
        Snake snake = new Snake(10, 10);
        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
        snake.moveAndEat();
        snake.setDirection(Snake.Direction.LEFT);
        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
        snake.setDirection(Snake.Direction.UP);
        assertEquals(Snake.Direction.UP, snake.getDirection());
    }

    @Test
    void moveTest() {
        Snake snake = new Snake(10, 10);
        assertEquals(1, snake.getBody().size());
        snake.moveAndEat();
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(1, 0), snake.getBody().peekFirst());
    }

    @Test
    void bumpedTest() {
        Snake snake = new Snake(10, 10);
        assertFalse(snake.bumped());
    }
}