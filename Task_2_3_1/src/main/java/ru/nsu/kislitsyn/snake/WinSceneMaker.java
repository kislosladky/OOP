package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class WinSceneMaker {
    public Scene get(Stage stage, SnakeController snakeController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("win-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        WinController winController = fxmlLoader.getController();
        winController.setSnakeController(snakeController);
        winController.setStage(stage);
        return scene;
    }
}

