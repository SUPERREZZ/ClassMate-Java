package com.eduapp.edumanagerapp.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
    private static final String test = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(test, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
