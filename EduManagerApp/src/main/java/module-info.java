module com.eduapp.edumanagerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.desktop;
    requires java.management;
    opens com.eduapp.edumanagerapp to javafx.fxml;
    opens com.eduapp.edumanagerapp.models to javafx.base;
    exports com.eduapp.edumanagerapp.Controller;
    opens com.eduapp.edumanagerapp.Controller to javafx.fxml;
    exports com.eduapp.edumanagerapp.Views;
    opens com.eduapp.edumanagerapp.Views to javafx.fxml;
}