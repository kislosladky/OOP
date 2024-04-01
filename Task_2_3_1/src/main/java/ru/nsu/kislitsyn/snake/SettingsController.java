package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


public class SettingsController {
    private Stage stage;

    @FXML
    private ChoiceBox<Integer> width;
    @FXML
    private ChoiceBox<Integer> height;
    private SnakeController snakeController;

    @FXML
    private void changeWidth() {
        Integer selectedOption = width.getValue();
        snakeController.setColumns(selectedOption);
    }

    @FXML
    private void changeHeight() {
        Integer selectedOption = height.getValue();
        snakeController.setLines(selectedOption);
    }

    @FXML
    private void goToTheGame() {
        stage.setScene(SnakeApplication.scenes.get(0));
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSnakeController(SnakeController snakeController) {
        this.snakeController = snakeController;
    }
}
