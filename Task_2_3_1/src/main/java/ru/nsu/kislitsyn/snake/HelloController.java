package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Canvas canvas;

    private static final int ROWS = 10;
    private static final int COLS = 10;

    @FXML
    public void initialize() {
        drawGrid();
    }

    private void drawGrid() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double cellWidth = width / COLS;
        double cellHeight = height / ROWS;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                double x = col * cellWidth;
                double y = row * cellHeight;
                gc.strokeRect(x, y, cellWidth, cellHeight);
            }
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hi!");
    }
}