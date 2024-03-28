package ru.nsu.kislitsyn.snake;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class HelloController {

    @FXML
    private Canvas canvas;
    //    @FXML
//    private Button buttonStart;
    private static final int rows = 10;
    private static final int columns = 10;

    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
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
                go();
            }
        }
    };
    public Snake getSnake() {
        return snake;
    }


    public void setSnake() {
        this.snake = new Snake(cellWidth);
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
        gc.setFill(Color.GREEN);
        for (Point point : body) {
            gc.fillRect(point.x(), point.y(), cellHeight, cellHeight);
        }
        if (lastBodyCell != null) {
            gc.setFill(Color.WHITE);
            gc.fillRect(lastBodyCell.x(), lastBodyCell.y(), cellHeight, cellWidth);
        }
    }


    public void go() {
        try {
            lastBodyCell = snake.moveAndEat();
        } catch (BumpedException e) {
            System.err.println("Bumped");
        }

        draw();
    }
    private void drawGrid() {
        gc = canvas.getGraphicsContext2D();
        width =(int)canvas.getWidth();
        height = (int)canvas.getHeight();
        cellWidth = width / columns;
        cellHeight = height / rows;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        int x = 0;
        int y = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                gc.strokeRect(x, y, cellWidth, cellHeight);
                gc.fillRect(x, y, cellWidth, cellHeight);
                x += cellWidth;
            }
            y += cellHeight;
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