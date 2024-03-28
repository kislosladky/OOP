package ru.nsu.kislitsyn.snake;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Deque;

public class SnakeController {

    @FXML
    private Canvas canvas;
    //    @FXML
//    private Button buttonStart;
    private static final int rows = 10;
    private static final int columns = 10;

    private int width;
    private int height;
    private int cellSize;
//    private int cellHeight;
    private GraphicsContext gc;
    private Snake snake;
    private Point lastBodyCell;
    @FXML
    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0;
        @FXML
        @Override
        public void handle(long now) {
            if (now - lastUpdate > 200_000_000) {
                lastUpdate = now;
//            try {
//                Thread.sleep(l);
//            } catch (InterruptedException e) {
//
//            }
                try {
                    go();
                } catch (BumpedException e) {
                    timer.stop();
                }
            }
        }
    };
    public Snake getSnake() {
        return snake;
    }


    public void setSnake() {
        this.snake = new Snake(cellSize);
    }

    public void setDirection(Snake.Direction direction) {
        snake.setDirection(direction);
    }
    @FXML
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
            gc.fillRect(apple.x() * cellSize, apple.y() * cellSize, cellSize, cellSize);
        }
        if (lastBodyCell != null) {
            gc.setFill(Color.WHITE);
            gc.fillRect(lastBodyCell.x() * cellSize, lastBodyCell.y() * cellSize, cellSize, cellSize);
            gc.strokeRect(lastBodyCell.x() * cellSize, lastBodyCell.y() * cellSize, cellSize, cellSize);
        }


    }


    public void go() throws BumpedException{
            lastBodyCell = snake.moveAndEat();
            draw();
    }

    @FXML
    private void drawGrid() {
        gc = canvas.getGraphicsContext2D();
        width =(int)canvas.getWidth();
        height = (int)canvas.getHeight();
        cellSize = width / columns;
//        cellHeight = height / rows;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        int x = 0;
        int y = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                gc.strokeRect(x * cellSize, y * cellSize, cellSize, cellSize);
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                x ++;
            }
            y++;
            x = 0;
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Hi!");
//    }
}