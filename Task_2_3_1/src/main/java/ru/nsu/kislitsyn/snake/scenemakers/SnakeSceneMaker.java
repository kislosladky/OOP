package ru.nsu.kislitsyn.snake.scenemakers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Getter;
import ru.nsu.kislitsyn.snake.snakes.Snake;
import ru.nsu.kislitsyn.snake.SnakeApplication;
import ru.nsu.kislitsyn.snake.controllers.SnakeController;

/**
 * Makes the scene of the game screen.
 */
@Getter
public class SnakeSceneMaker {
    private SnakeController snakeController;

    /**
     * Loads the scene from fxml file.
     */
    public Scene get(Stage stage) throws IOException {
        FXMLLoader snakeLoader =
                new FXMLLoader(SnakeApplication.class.getResource("snake-view.fxml"));
        Scene snakeScene = new Scene(snakeLoader.load(), 800, 710);
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
        snakeController = snakeLoader.getController();
        snakeController.setStage(stage);
        snakeController.setSnake();

        return snakeScene;
    }
}
