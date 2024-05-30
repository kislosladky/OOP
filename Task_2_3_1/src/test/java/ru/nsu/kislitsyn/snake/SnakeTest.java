//package ru.nsu.kislitsyn.snake;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//import org.junit.jupiter.api.Test;
//
//
//class SnakeTest {
//    @Test
//    void setDirectionTest() {
//        Apples apples = new Apples()
//        Snake snake = new Snake(10, 10);
//        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
//        snake.moveAndEat();
//        snake.setDirection(Snake.Direction.LEFT);
//        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
//        snake.setDirection(Snake.Direction.UP);
//        assertEquals(Snake.Direction.UP, snake.getDirection());
//    }
//
//    @Test
//    void moveTest() {
//        Snake snake = new Snake(10, 10);
//        assertEquals(1, snake.getBody().size());
//        snake.moveAndEat();
//        assertEquals(1, snake.getBody().size());
//        assertEquals(new Point(1, 0), snake.getBody().peekFirst());
//    }
//
//    @Test
//    void bumpedTest() {
//        Snake snake = new Snake(10, 10, null);
//        assertFalse(snake.bumped());
//    }
//
//    @Test
//    void initTest() {
//        Snake snake = new Snake(10, 10, null);
////        assertEquals(2, snake.getApples().size());
//        assertEquals(new Point(0, 0), snake.getBody().peekFirst());
//    }
//
//    @Test
//    void restartTest() {
//        Snake snake = new Snake(10, 10);
//        snake.moveAndEat();
//        snake.setDirection(Snake.Direction.UP);
//        snake.moveAndEat();
//        snake.restart();
//
//        assertEquals(1, snake.getBody().size());
//        assertEquals(new Point(0, 0), snake.getBody().peekFirst());
//        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
//        assertEquals(3, snake.getApples().size());
//    }
//
//    @Test
//    void tailTest() {
//        Snake snake = new Snake(10, 10);
//        Point tail = snake.moveAndEat();
//        assertEquals(new Point(0, 0), tail);
//    }
//}