module com.group10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.json;

    opens com.group10 to javafx.fxml;
    exports com.group10;
}
