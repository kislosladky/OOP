package ru.nsu.kislitsyn.snake.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import ru.nsu.kislitsyn.snake.SnakeApplication;
import ru.nsu.kislitsyn.snake.controllers.SnakeController;

/**
 * A controller for the screen of win.
 */
public class WinController {
    private Stage stage;
    private SnakeController snakeController;

    /**
     * Setter for the stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * I use snake controller to adjust some parameters of it, so here is the setter for it.
     */
    public void setSnakeController(SnakeController snakeController) {
        this.snakeController = snakeController;
    }

    /**
     * Speeds up the snake.
     */
    @FXML
    private void nextLevel() {
        double speed = snakeController.timer.getSpeed();

        speed = speed * 0.9;
        snakeController.timer.setSpeed(speed);

        goToTheGame();
    }

    /**
     * Changes the scene.
     */
    private void goToTheGame() {
        stage.setScene(SnakeApplication.scenes.get(0));
        stage.show();
    }
}
