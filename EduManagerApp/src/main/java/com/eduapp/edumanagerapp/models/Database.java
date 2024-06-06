package com.eduapp.edumanagerapp.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
    private static final String test = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres";
    private static final String USERNAME = "postgres.fkrmgqmyawbzueewvdlq";
    private static final String PASSWORD = "vNkBiSRNW0P0B8tO";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(test, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
