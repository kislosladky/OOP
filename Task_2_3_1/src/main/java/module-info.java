module ru.nsu.kislitsyn.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.kislitsyn.snake to javafx.fxml;
    exports ru.nsu.kislitsyn.snake;
}