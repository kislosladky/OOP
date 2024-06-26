package ru.nsu.kislitsyn.snake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.kislitsyn.snake.controllers.SnakeController;
import ru.nsu.kislitsyn.snake.scenemakers.SettingsSceneMaker;
import ru.nsu.kislitsyn.snake.scenemakers.SnakeSceneMaker;
import ru.nsu.kislitsyn.snake.scenemakers.WinSceneMaker;


/**
 * Main class of the game the initializes all screens.
 */
public class SnakeApplication extends Application {
    public static List<Scene> scenes = new ArrayList<>();

    /**
     * Runs the app.
     */
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
        stage.setMinHeight(710);
        stage.setMinWidth(760);

    }

    /**
     * Main.
     */
    public static void main(String[] args) {
        launch();
    }
}