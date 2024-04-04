package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


/**
 * Controller of screen of settings.
 */
public class SettingsController {
    private Stage stage;

    @FXML
    private ChoiceBox<Integer> width;
    @FXML
    private ChoiceBox<Integer> height;
    private SnakeController snakeController;

    /**
     * Changes the width of the game field.
     */
    @FXML
    private void changeWidth() {
        Integer selectedOption = width.getValue();
        snakeController.setColumns(selectedOption);
    }

    /**
     * Changes the height of the game field.
     */
    @FXML
    private void changeHeight() {
        Integer selectedOption = height.getValue();
        snakeController.setLines(selectedOption);
    }

    /**
     * Changes the scene to the game screen.
     */
    @FXML
    private void goToTheGame() {
        stage.setScene(SnakeApplication.scenes.get(0));
        stage.show();
    }

    /**
     * Setter for stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * We need a snake controller for some operations, so here is setter for it.
     */
    public void setSnakeController(SnakeController snakeController) {
        this.snakeController = snakeController;
    }
}
