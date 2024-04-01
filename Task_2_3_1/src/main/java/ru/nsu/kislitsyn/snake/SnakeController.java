package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Deque;


public class SnakeController {
    private Stage stage;
    @FXML
    private Canvas canvas;
    private int lines = 16;
    private int columns = 16;

    private int width;
    private int height;
    private int cellSize;
    //    private int cellHeight;
    private GraphicsContext gc;
    private Snake snake;
    private Point lastBodyCell;

    public void setLines(int lines) {
        this.lines = lines;
        snake.setHeight(lines);
        System.out.println(lines);
        canvas.setHeight(lines * cellSize);
        snake.restart();
        drawGrid();
    }

    public void setColumns(int columns) {
        this.columns = columns;
        snake.setWidth(columns);
        canvas.setWidth(columns * cellSize);
        drawGrid();
        System.out.println(columns);
        snake.restart();
    }

    public Timer timer = new Timer(200) {

        @Override
        public void handle(long now) {
            if (now - getPrevTime() > getSpeed() * 1_000_000) {
                setPrevTime(now);
                go();
            }
        }
    };

    public Snake getSnake() {
        return snake;
    }


    public void setSnake() {
        this.snake = new Snake(columns, lines);
    }

    public void setDirection(Snake.Direction direction) {
        snake.setDirection(direction);
    }

    public void initialize() {
        drawGrid();
    }

    @FXML
    void letsgo() {
        timer.start();
    }

    @FXML
    public void draw() {
        ArrayList<Point> body = new ArrayList<>(snake.getBody());
        Deque<Point> apples = snake.getApples();
        gc.setFill(Color.GREEN);
        for (Point point : body) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }
        gc.setFill(Color.RED);
        for (Point apple : apples) {
            gc.fillOval(apple.x() * cellSize, apple.y() * cellSize, cellSize, cellSize);
        }
        if (lastBodyCell != null) {
            gc.setFill(Color.WHITE);
            gc.fillRect(lastBodyCell.x() * cellSize, lastBodyCell.y() * cellSize, cellSize, cellSize);
        }


    }


    public void go() {
        lastBodyCell = snake.moveAndEat();

        if (snake.getBody().size() == 12) {
            clearBody();
            snake.restart();
            timer.stop();
            goToNextLevel();
        }
        if (snake.bumped()) {
            clearBody();
            snake.restart();
            timer.stop();
        }
        draw();
    }

    @FXML
    private void drawGrid() {
        gc = canvas.getGraphicsContext2D();
        cellSize = 32;

        width = cellSize * columns;
        height = cellSize * lines;
        canvas.setHeight(height);
        canvas.setWidth(width);

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        int x = 0;
        int y = 0;
        for (int row = 0; row < lines; row++) {
            for (int col = 0; col < columns; col++) {
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                x++;
            }
            y++;
            x = 0;
        }
    }

    private void clearBody() {
        gc.setFill(Color.WHITE);
        for (Point point : snake.getBody()) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }

        for (Point point : snake.getApples()) {
            gc.fillRect(point.x() * cellSize, point.y() * cellSize, cellSize, cellSize);
        }
        if (lastBodyCell != null) {
            gc.fillRect(lastBodyCell.x() * cellSize, lastBodyCell.y() * cellSize, cellSize, cellSize);
        }
    }

    @FXML
    void openSettings() {
        timer.stop();
        stage.setScene(SnakeApplication.scenes.get(1));
        stage.show();
    }

    void goToNextLevel() {
        stage.setScene(SnakeApplication.scenes.get(2));
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}