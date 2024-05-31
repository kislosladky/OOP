module ru.nsu.kislitsyn.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens ru.nsu.kislitsyn.snake to javafx.fxml;
    exports ru.nsu.kislitsyn.snake;
    exports ru.nsu.kislitsyn.snake.scenemakers;
    opens ru.nsu.kislitsyn.snake.scenemakers to javafx.fxml;
    exports ru.nsu.kislitsyn.snake.controllers;
    opens ru.nsu.kislitsyn.snake.controllers to javafx.fxml;
    exports ru.nsu.kislitsyn.snake.snakes;
    opens ru.nsu.kislitsyn.snake.snakes to javafx.fxml;
}