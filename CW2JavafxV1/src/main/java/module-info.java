module com.example.cw2javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cw2javafx to javafx.fxml;
    exports com.example.cw2javafx;
}