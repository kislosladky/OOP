package ru.nsu.kislitsyn.snake;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Makes the scene of the game screen.
 */
public class SnakeSceneMaker {
    private SnakeController snakeController;

    /**
     * Getter for snake controller that is used in other scenes for snake adjustment.
     */
    public SnakeController getSnakeController() {
        return snakeController;
    }

    /**
     * Loads the scene from fxml file.
     */
    public Scene get(Stage stage) throws IOException {
        FXMLLoader snakeLoader =
                new FXMLLoader(SnakeApplication.class.getResource("snake-view.fxml"));

        snakeController = snakeLoader.getController();
        snakeController.setStage(stage);
        snakeController.setSnake();

        Scene snakeScene = new Scene(snakeLoader.load(), 1600, 900);
        snakeScene.addEventHandler(KeyEvent.KEY_PRESSED, action -> {
            KeyCode keycode = action.getCode();
            switch (keycode) {
                case W: {
                    snakeController.setDirection(Snake.Direction.UP);
                    break;
                }
                case S: {
                    snakeController.setDirection(Snake.Direction.DOWN);
                    break;
                }
                case A: {
                    snakeController.setDirection(Snake.Direction.LEFT);
                    break;
                }
                case D: {
                    snakeController.setDirection(Snake.Direction.RIGHT);
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        });

        return snakeScene;
    }
}