package ru.nsu.kislitsyn.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setScene(scene);
        stage.setTitle("Grid Canvas Example");
        stage.show();
        HelloController controller = fxmlLoader.getController();
        controller.setSnake();
        Canvas canvas = controller.getCanvas();
        canvas.setOnKeyPressed(action -> {
            KeyCode keycode = action.getCode();
            switch (keycode) {
                case W: {
                    controller.setDirection(Snake.Direction.UP);
                    break;
                }
                case S: {
                    // set Down
                    controller.setDirection(Snake.Direction.DOWN);
                    break;
                }
                case A: {
                    controller.setDirection(Snake.Direction.LEFT);
                    break;
                }
                case D: {
                    //set right
                    controller.setDirection(Snake.Direction.RIGHT);
                    break;
                }

            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}