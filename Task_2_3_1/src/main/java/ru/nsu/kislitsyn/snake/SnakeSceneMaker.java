package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeSceneMaker {

    private Stage stage;

    public SnakeSceneMaker(Stage stage) {
        this.stage = stage;
    }
    public Scene get(Stage stage) throws IOException{
        FXMLLoader snakeLoader = new FXMLLoader(SnakeApplication.class.getResource("snake-view.fxml"));

        Scene snakeScene = new Scene(snakeLoader.load(), 1600, 900);


        SnakeController snakeController = snakeLoader.getController();
        snakeController.setStage(stage);
        snakeController.setSnake();
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

            }
        });

        return snakeScene;

    }
}
