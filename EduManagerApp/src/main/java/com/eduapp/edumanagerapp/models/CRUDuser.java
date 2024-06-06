package com.eduapp.edumanagerapp.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUDuser {

    public void Create(user user, Connection connection) {
        String queryInsert = "INSERT INTO users(nama,password,role) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(queryInsert)) {
            statement.setString(1, user.getNama());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
            System.out.println("Berhasil menambah data");
        } catch (SQLException e) {
            System.out.println("Gagal menambah data: " + e.getMessage());
        }
    }

    public void Delete(user user, Connection connection) {
        String queryDelete = "DELETE FROM user WHERE nama = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryDelete)) {
            statement.setString(1, user.getNama());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            System.out.println("Berhasil menghapus data");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
        }
    }

    public void Update(user user, Connection connection) {
        String queryUpdate = "UPDATE user SET nama = ?, password = ?, role = ? WHERE nama = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryUpdate)) {

            statement.setString(1, user.getNama());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
            System.out.println("Berhasil mengupdate data");
        } catch (SQLException e) {
            System.out.println("Gagal mengupdate data: " + e.getMessage());
        }
    }

    public user Read(String nama, String password, Connection connection) {
        String queryRead = "SELECT * FROM users WHERE nama = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryRead)) {
            statement.setString(1, nama);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new user(
                            resultSet.getString("nama"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getInt("Id")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }
        return null;
    }
}
