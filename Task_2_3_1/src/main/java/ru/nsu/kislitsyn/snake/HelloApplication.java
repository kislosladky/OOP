package ru.nsu.kislitsyn.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private int WIDTH = 400;
    private int HEIGHT = 400;
    private int COLUMNS = 10;
    private int ROWS = 10;

    @Override
    public void start(Stage stage) throws IOException {
//        Group root = new Group();
//        Scene s = new Scene(root, 300, 300, Color.BLACK);
//
//        final Canvas canvas = new Canvas(250,250);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        gc.setFill(Color.BLUE);
//        gc.fillRect(75,75,100,100);
//
//        root.getChildren().add(canvas);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
//        stage.setTitle("Hello world!");
//        stage.setScene(scene);
//        stage.show();
//        Group root = new Group();
//        Canvas canvas = new Canvas(WIDTH, HEIGHT);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        drawGrid(gc);

//        AnchorPane anchorPane = new AnchorPane();
//        anchorPane.getChildren().add(canvas);
//        root.getChildren().add(anchorPane);

//        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.setScene(scene);
        stage.setTitle("Grid Canvas Example");
        stage.show();
    }


//    private void drawGrid(GraphicsContext gc) {
//        double cellWidth = (double) WIDTH / COLUMNS;
//        double cellHeight = (double) HEIGHT / ROWS;
//
//        gc.setStroke(Color.BLACK);
//        gc.setLineWidth(1);
//
//        for (int row = 0; row < ROWS; row++) {
//            for (int col = 0; col < COLUMNS; col++) {
//                double x = col * cellWidth;
//                double y = row * cellHeight;
//                gc.strokeRect(x, y, cellWidth, cellHeight);
//            }
//        }
//    }
    public static void main(String[] args) {
        launch();
    }
}