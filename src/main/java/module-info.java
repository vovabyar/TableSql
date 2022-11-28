module com.example.table {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    opens com.example.table to javafx.fxml;
    //opens com.example.table.model to javafx.fxml;
    opens com.example.table.model to com.google.gson;

    exports com.example.table;
    exports com.example.table.model;

}