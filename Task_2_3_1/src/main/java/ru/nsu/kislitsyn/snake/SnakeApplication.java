package ru.nsu.kislitsyn.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SnakeApplication extends Application {
    public static List<Scene> scenes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        SnakeSceneMaker snakeSceneMaker = new SnakeSceneMaker();
        Scene snakeScene = snakeSceneMaker.get(stage);
        SnakeController snakeController = snakeSceneMaker.getSnakeController();
        scenes.add(snakeScene);
        Scene settingsScene = new SettingsSceneMaker().get(stage, snakeController);
        scenes.add(settingsScene);
        Scene winScene = new WinSceneMaker().get(stage, snakeController);
        scenes.add(winScene);
        stage.setScene(snakeScene);
        stage.setTitle("Snake game");
        stage.show();
        stage.setMinHeight(500);
        stage.setMinWidth(700);

    }

    public static void main(String[] args) {
        launch();
    }
}