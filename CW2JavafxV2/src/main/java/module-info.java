module com.example.cw2javafxv2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cw2javafxv2 to javafx.fxml;
    exports com.example.cw2javafxv2;
}