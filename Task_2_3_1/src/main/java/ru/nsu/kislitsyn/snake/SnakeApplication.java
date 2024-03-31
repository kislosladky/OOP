package ru.nsu.kislitsyn.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SnakeApplication extends Application {
    public static List<Scene> scenes = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader snakeLoader = new FXMLLoader(SnakeApplication.class.getResource("snake-view.fxml"));
//        Scene snakeScene = new Scene(snakeLoader.load(), 1600, 900);
        Scene snakeScene = new SnakeSceneMaker(stage).get(stage);
        scenes.add(snakeScene);

//        FXMLLoader settingsLoader = new FXMLLoader(SnakeApplication.class.getResource("settings-view.fxml"));
//        Scene settingsScene = new Scene(settingsLoader.load(), 1600, 900);
        Scene settingsScene = new SettingsSceneMaker().get(stage);
        scenes.add(settingsScene);
//        stage.setScene(settingsScene);
        stage.setScene(snakeScene);
        stage.setTitle("Snake game");
        stage.show();
        stage.setMinHeight(500);
        stage.setMinWidth(700);
//        SnakeController snakeController = snakeLoader.getController();
//        snakeController.setSnake();
//        Canvas snakeCanvas = snakeController.getCanvas();
//        snakeScene.addEventHandler(KeyEvent.KEY_PRESSED, action -> {
//            KeyCode keycode = action.getCode();
//            switch (keycode) {
//                case W: {
//                    snakeController.setDirection(Snake.Direction.UP);
//                    break;
//                }
//                case S: {
//                    snakeController.setDirection(Snake.Direction.DOWN);
//                    break;
//                }
//                case A: {
//                    snakeController.setDirection(Snake.Direction.LEFT);
//                    break;
//                }
//                case D: {
//                    snakeController.setDirection(Snake.Direction.RIGHT);
//                    break;
//                }
//
//            }
//        });
    }

    public static void main(String[] args) {
        launch();
    }
}