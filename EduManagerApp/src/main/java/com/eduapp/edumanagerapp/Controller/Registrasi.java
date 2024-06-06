package com.eduapp.edumanagerapp.Controller;


import com.eduapp.edumanagerapp.Views.LoginViews;
import com.eduapp.edumanagerapp.models.CRUDuser;
import com.eduapp.edumanagerapp.models.Database;
import com.eduapp.edumanagerapp.models.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Registrasi {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public Registrasi() {
    }


    @FXML
    public void onRegistrasiButtonClick(ActionEvent actionEvent) {
        try (Connection connection = Database.getConnection()) {
            System.out.println("Koneksi terhubung");
            String nama = username.getText();
            String pw = password.getText();
            if (nama.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username or Password cannot be empty");
                return;
            }
            user user = new user(nama, pw, "Anggota");
            CRUDuser cruduser = new CRUDuser();
            cruduser.Create(user, connection);
            JOptionPane.showMessageDialog(null, "Registration Successfull");
            Stage currentStage = (Stage) username.getScene().getWindow();
            currentStage.close();
            LoginViews helloApp = new LoginViews(new Stage());
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SwitchToLogin(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) username.getScene().getWindow();
        currentStage.close();
        LoginViews helloApp = new LoginViews(new Stage());

    }

}
