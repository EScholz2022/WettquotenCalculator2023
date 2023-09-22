module com.example.wettquotencalculator2023 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.wettquotencalculator2023 to javafx.fxml;
    exports com.example.wettquotencalculator2023;
}