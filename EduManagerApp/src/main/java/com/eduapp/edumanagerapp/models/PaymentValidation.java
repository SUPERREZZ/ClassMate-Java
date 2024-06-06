package com.eduapp.edumanagerapp.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentValidation {
    public static boolean isPaymentValid(int userId) {
        LocalDate lastPaymentDate = getLastPaymentDate(userId);
        System.out.println("cekcek" + lastPaymentDate);
        if (lastPaymentDate == null) {
            return true;
        } else {
            LocalDate oneWeekAgo = LocalDate.now().minusDays(6);
            System.out.println("BRUHF" + lastPaymentDate.isBefore(oneWeekAgo));
            System.out.println(oneWeekAgo);
            return lastPaymentDate.isBefore(oneWeekAgo);

        }
    }

    private static LocalDate getLastPaymentDate(int userId) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT MAX(Tanggal) FROM kas WHERE Id_User = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        java.sql.Date sqlDate = resultSet.getDate(1);
                        if (sqlDate != null) {
                            return sqlDate.toLocalDate();
                        } else {
                            return null;
                        }
                    }
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPaymentStatus(int userId) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT Keterangan FROM kas WHERE Id_User = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("Keterangan");
                    }
                }
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Jika tidak ada data ditemukan, kembalikan string kosong
        return "";
    }

    public static void updatePaymentStatus(int userId, String newPaymentStatus,int idTransaksi) {
        try (Connection connection = Database.getConnection()) {
            String query = "UPDATE kas SET Keterangan = ? WHERE Id_User = ? AND id_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newPaymentStatus);
                preparedStatement.setInt(2, userId);
                preparedStatement.setInt(3, idTransaksi);
                preparedStatement.executeUpdate();
            }
            Database.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

