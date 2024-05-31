package ru.nsu.kislitsyn.snake.controllers;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Setter;
import ru.nsu.kislitsyn.snake.*;
import ru.nsu.kislitsyn.snake.Timer;
import ru.nsu.kislitsyn.snake.snakes.HunterSnake;
import ru.nsu.kislitsyn.snake.snakes.RobotSnake;
import ru.nsu.kislitsyn.snake.snakes.Snake;


/**
 * The controller of the main scene with game.
 */
public class SnakeController {
    @Setter
    private Stage stage;
    @FXML
    private Label score;
    @FXML
    private Canvas canvas;
    private int lines = 16;
    private int columns = 16;
    private int cellSize;
    private GraphicsContext gc;

    private Snake snake;
//    private RobotSnake robot;
    private HunterSnake robot;
    private Apples apples;
    private Deque<Point> lastBodyCells = new ArrayDeque<>();
    private int level = 1;
    private int totalLength = 2;
    /**
     * Sets the number of lines in the field and adjusts the canvas for it.
     */
    public void setLines(int lines) {
        this.lines = lines;
        snake.setHeight(lines);
        robot.setHeight(lines);
        canvas.setHeight(lines * cellSize);
        apples.setHeight(lines);
        restart();
        prepareField();
    }

    /**
     * Sets the number of columns in the field and adjusts the canvas for it.
     */
    public void setColumns(int columns) {
        this.columns = columns;
        snake.setWidth(columns);
        robot.setWidth(columns);
        canvas.setWidth(columns * cellSize);
        apples.setWidth(columns);
        prepareField();
        System.out.println(columns);

        restart();
    }

    public Timer timer = new Timer(200) {
        /**
         * Slowing down the snake using the speed field of the timer.
         */
        @Override
        public void handle(long now) {
            if (now - getPrevTime() > getSpeed() * 1_000_000) {
                setPrevTime(now);
                go();
            }
        }
    };

    /**
     * Sets a new snake to the game.
     */
    public void setSnake() {
        apples = new Apples(columns, lines);
        snake = new Snake(columns, lines, apples);
//        robot = new RobotSnake(columns, lines, apples);
        robot = new HunterSnake(columns, lines, apples, snake.getBody());

        spawnApples();
    }

    /**
     * Sets direction of snake movement.
     */
    public void setDirection(Snake.Direction direction) {
        snake.setDirection(direction);
    }


    /**
     * Initializes the field for the snake.
     */
    public void initialize() {
        prepareField();
    }

    /**
     * Starts the animation timer by pressing button.
     */
    @FXML
    void letsgo() {
        timer.start();
    }

    /**
     * Draws the snake and apples.
     */
    @FXML
    public void draw() {
        ArrayList<Point> body = new ArrayList<>(snake.getBody());
        gc.setFill(Color.GREEN);

        for (Point point : body) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }

        body = new ArrayList<>(robot.getBody());
        gc.setFill(Color.BLUE);

        for (Point point : body) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }

        Deque<Point> applePoints = this.apples.getApples();
        gc.setFill(Color.RED);
        for (Point apple : applePoints) {
            gc.fillOval(apple.x() * cellSize, apple.y() * cellSize, cellSize, cellSize);
        }

        gc.setFill(Color.WHITE);
        int length = lastBodyCells.size();
        for (int i = 0; i < length; i++) {
            Point cell = lastBodyCells.pollFirst();
            if (!Snake.intersectAny(cell, List.of(snake.getBody(), robot.getBody()))) {
                gc.fillRect(cell.x() * cellSize,
                        cell.y() * cellSize, cellSize, cellSize);
            }
        }
    }


    /**
     * Moves the snake.
     * Increases the level, if the snake is long enough.
     * Restarts the game if the snake bumped into itself.
     */
    public void go() {
        lastBodyCells.add(snake.moveAndEat());
        lastBodyCells.add(robot.moveAndEat());
        if (totalLength < snake.getBody().size() + robot.getBody().size()) {
            for (int i = 0; i < snake.getBody().size()
                    + robot.getBody().size() - totalLength; i++) {
                apples.spawnApple(List.of(snake.getBody(), robot.getBody()));
            }
            totalLength = snake.getBody().size() + robot.getBody().size();
        }

        score.setText("" + snake.getBody().size());
        if (snake.getBody().size() == lines * columns / 8) {
            clear();
            restart();
            timer.stop();
            goToNextLevel();
        }
        if (snake.bumped() || Snake.intersect(snake.getBody().peekFirst(), robot.getBody())) {
            clear();
            restart();
            score.setText("0");
            timer.stop();
        }

        if (robot.bumped() || Snake.intersect(robot.getBody().peekFirst(), snake.getBody())) {
            clear();
            robot.restart();
            totalLength = snake.getBody().size() + 1;
        }


        draw();
    }

    /**
     * Prepares the canvas for the game.
     */
    @FXML
    private void prepareField() {
        gc = canvas.getGraphicsContext2D();
        cellSize = 32;

        int width = cellSize * columns;
        int height = cellSize * lines;
        canvas.setHeight(height);
        canvas.setWidth(width);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
    }

    /**
     * Clears the body and apples.
     */
    private void clear() {
        gc.setFill(Color.WHITE);
        for (Point point : snake.getBody()) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }

        for (Point point : robot.getBody()) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }

        for (Point point : apples.getApples()) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }
    }

    /**
     * Changes the current scene to settings screen.
     */
    @FXML
    void openSettings() {
        timer.stop();
        stage.setScene(SnakeApplication.scenes.get(1));
        stage.show();
    }

    /**
     * Changes the current scene to the win screen.
     */
    void goToNextLevel() {
        stage.setScene(SnakeApplication.scenes.get(2));
        stage.show();
//        snake.increaseLevel();
        level++;
    }

    void spawnApples() {
        for (int i = 0; i < level + 2; i++) {
            apples.spawnApple(List.of(snake.getBody(), robot.getBody()));
        }
    }

    void restart() {
        apples.restart();
        snake.restart();
        robot.restart();
        spawnApples();
        totalLength = 2;
    }
}