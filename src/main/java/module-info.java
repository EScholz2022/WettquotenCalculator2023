module com.example.wettquotencalculator2023 {
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires org.jsoup;
    requires jxl;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.controlsfx.controls;

    opens com.example.wettquotencalculator2023 to javafx.fxml;
    exports sample;
    exports com.example.wettquotencalculator2023;
    opens sample to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}