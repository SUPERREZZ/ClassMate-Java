package com.eduapp.edumanagerapp.Controller;

import com.eduapp.edumanagerapp.Views.Dashboard;
import com.eduapp.edumanagerapp.Views.LoginViews;
import com.eduapp.edumanagerapp.Views.RegistrasiViews;
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

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public LoginController() {

    }

    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        try (Connection connection = Database.getConnection()) {
            String nama = username.getText();
            String pw = password.getText();
            CRUDuser cruduser = new CRUDuser();
            user user = cruduser.Read(nama, pw, connection);
            if (user != null) {
                if (user.getRole().equals("Anggota")) {
                    DashboardAnggota.setUserData(user);
                    JOptionPane.showMessageDialog(null, "Login Successfull as " + user.getNama() + " " + user.getRole());
                    Dashboard dashboard = new Dashboard(new Stage(), user.getRole());
                    Stage currentStage = (Stage) username.getScene().getWindow();
                    currentStage.close();
                } else if (user.getRole().equals("Admin")) {
                    DashboardAdmin.setUserData(user);
                    JOptionPane.showMessageDialog(null, "Login Successfull as " + user.getNama() + " " + user.getRole());
                    Dashboard dashboard = new Dashboard(new Stage(), user.getRole());
                    Stage currentStage = (Stage) username.getScene().getWindow();
                    currentStage.close();
                }
            } else {
                // Jika user tidak ditemukan, tampilkan pesan kesalahan
                JOptionPane.showMessageDialog(null, "Username or Password is incorrect");
                username.clear();
                password.clear();
                // Tampilkan kembali form login
                LoginViews loginViews = new LoginViews((Stage) username.getScene().getWindow());
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickSwitchForm(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) username.getScene().getWindow();
        currentStage.close();
        RegistrasiViews registrasiViews = new RegistrasiViews(new Stage());
    }
}
