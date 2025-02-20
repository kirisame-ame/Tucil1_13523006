module kirisame {
    requires javafx.controls;
    requires javafx.fxml;

    opens kirisame to javafx.fxml;
    exports kirisame;
}
