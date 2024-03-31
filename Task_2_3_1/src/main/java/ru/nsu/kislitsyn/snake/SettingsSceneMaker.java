package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsSceneMaker {

    public Scene get(Stage stage) throws IOException {
        FXMLLoader settingsLoader = new FXMLLoader(SnakeApplication.class.getResource("settings-view.fxml"));
        Scene settingsScene = new Scene(settingsLoader.load(), 1600, 900);
        SettingsController settingsController = settingsLoader.getController();
        settingsController.setStage(stage);
        return settingsScene;
    }
}
