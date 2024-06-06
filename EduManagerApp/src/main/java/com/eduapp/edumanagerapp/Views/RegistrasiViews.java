package com.eduapp.edumanagerapp.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class RegistrasiViews extends Application {
    private Stage stage; // Variabel untuk menyimpan objek Stage
    public RegistrasiViews() {}
    public RegistrasiViews(Stage stage) throws IOException {
        this.stage = stage;
        start(stage);
    }
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = (stage != null) ? stage : new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrasiViews.class.getResource("/com/eduapp/edumanagerapp/Registarsi.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        this.stage.setTitle("Registration Form");
        this.stage.setScene(scene);
        this.stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
