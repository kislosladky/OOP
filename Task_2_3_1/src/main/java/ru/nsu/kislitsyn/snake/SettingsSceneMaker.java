package ru.nsu.kislitsyn.snake;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The class that makes the settings scene.
 */
public class SettingsSceneMaker {

    /**
     * This function loads the settings scene from fxml.
     */
    public Scene get(Stage stage, SnakeController snakeController) throws IOException {
        FXMLLoader settingsLoader = new FXMLLoader(SnakeApplication.class.getResource("settings-view.fxml"));
        Scene settingsScene = new Scene(settingsLoader.load(), 1600, 900);
        SettingsController settingsController = settingsLoader.getController();
        settingsController.setSnakeController(snakeController);
        settingsController.setStage(stage);
        return settingsScene;
    }
}
