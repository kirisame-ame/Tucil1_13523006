module kirisame {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.desktop;
    requires javafx.swing;

    opens kirisame to javafx.fxml;
    exports kirisame;
}
