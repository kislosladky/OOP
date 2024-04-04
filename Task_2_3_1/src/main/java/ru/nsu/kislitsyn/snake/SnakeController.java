package ru.nsu.kislitsyn.snake;

import java.util.ArrayList;
import java.util.Deque;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * The controller of the main scene with game.
 */
public class SnakeController {
    private Stage stage;
    @FXML
    private Canvas canvas;
    private int lines = 16;
    private int columns = 16;
    private int cellSize;
    private GraphicsContext gc;
    private Snake snake;
    private Point lastBodyCell;

    /**
     * Sets the number of lines in the field and adjusts the canvas for it.
     */
    public void setLines(int lines) {
        this.lines = lines;
        snake.setHeight(lines);
        canvas.setHeight(lines * cellSize);
        snake.restart();
        prepareField();
    }

    /**
     * Sets the number of columns in the field and adjusts the canvas for it.
     */
    public void setColumns(int columns) {
        this.columns = columns;
        snake.setWidth(columns);
        canvas.setWidth(columns * cellSize);
        prepareField();
        System.out.println(columns);
        snake.restart();
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
     * Getter for the snake.
     */
    public Snake getSnake() {
        return snake;
    }


    /**
     * Sets a new snake to the game.
     */
    public void setSnake() {
        this.snake = new Snake(columns, lines);
    }

    /**
     * Sets direction of snake movement.
     */
    public void setDirection(Snake.Direction direction) {
        snake.setDirection(direction);
    }

    /**
     * Setter for the stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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

        Deque<Point> apples = snake.getApples();
        gc.setFill(Color.RED);
        for (Point apple : apples) {
            gc.fillOval(apple.x() * cellSize, apple.y() * cellSize, cellSize, cellSize);
        }
        if (lastBodyCell != null) {
            gc.setFill(Color.WHITE);
            gc.fillRect(lastBodyCell.x() * cellSize,
                    lastBodyCell.y() * cellSize, cellSize, cellSize);
        }


    }


    /**
     * Moves the snake.
     * Increases the level, if the snake is long enough.
     * Restarts the game if the snake bumped into itself.
     */
    public void go() {
        lastBodyCell = snake.moveAndEat();

        if (snake.getBody().size() == lines * columns / 8) {
            clear();
            snake.restart();
            timer.stop();
            goToNextLevel();
        }
        if (snake.bumped()) {
            clear();
            snake.restart();
            timer.stop();
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

        for (Point point : snake.getApples()) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }

        //if (lastBodyCell != null) {
        //    gc.fillRect(lastBodyCell.x() * cellSize, lastBodyCell.y() * cellSize, cellSize, cellSize);
        //}
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
        snake.increaseLevel();
    }
}