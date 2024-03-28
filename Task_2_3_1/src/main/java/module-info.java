module ru.nsu.kislitsyn.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.kislitsyn.snake to javafx.fxml;
    exports ru.nsu.kislitsyn.snake;
}