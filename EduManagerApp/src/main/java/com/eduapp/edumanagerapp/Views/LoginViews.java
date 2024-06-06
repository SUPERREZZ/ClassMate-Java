package com.eduapp.edumanagerapp.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class LoginViews {
    private final Stage stage;
    public LoginViews(Stage stage) throws IOException {
        this.stage = stage;
        start(stage);
    }
    public LoginViews() {this.stage = null;}
    public void start(Stage stage) throws IOException {
        Stage currentStage = (stage != null) ? stage : this.stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginViews.class.getResource("/com/eduapp/edumanagerapp/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        currentStage.setTitle("Login Form");
        currentStage.setScene(scene);
        currentStage.show();
    }
}