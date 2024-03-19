module com.example.scrabble1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.scrabble1 to javafx.fxml;
    exports com.example.scrabble1;
}