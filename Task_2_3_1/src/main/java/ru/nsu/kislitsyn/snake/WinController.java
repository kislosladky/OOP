package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class WinController {
    private Stage stage;
    private SnakeController snakeController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSnakeController(SnakeController snakeController) {
        this.snakeController = snakeController;
    }

    @FXML
    private void nextLevel() {
        long speed = snakeController.timer.getSpeed();
        speed -= 40;
        snakeController.timer.setSpeed(speed);

        goToTheGame();
    }

    private void goToTheGame() {
        stage.setScene(SnakeApplication.scenes.get(0));
        stage.show();
    }
}
