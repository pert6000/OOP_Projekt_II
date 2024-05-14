module org.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projekt to javafx.fxml;
    exports org.example.projekt;
}