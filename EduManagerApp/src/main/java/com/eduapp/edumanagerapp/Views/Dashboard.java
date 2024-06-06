package com.eduapp.edumanagerapp.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class Dashboard {
    private final Stage stage;
    private final String role;
    public Dashboard(Stage stage, String role) throws IOException {
        this.stage = stage;
        this.role = role;
        start(this.stage, this.role);
    }
    public void start(Stage stage, String role) throws IOException {
        if (role.equals("Anggota")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("/com/eduapp/edumanagerapp/DashboardAnggota.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            stage.setTitle("Dashboard Member");
            stage.setScene(scene);
            stage.show();
        } else if (role.equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("/com/eduapp/edumanagerapp/DashboardAdmin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            stage.setTitle("Dashboard Admin");
            stage.setScene(scene);
            stage.show();
        }
    }
}
