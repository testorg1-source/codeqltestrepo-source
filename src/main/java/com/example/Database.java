package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String url = System.getenv("DB_URL");
    private final String user = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASSWORD");

    public Connection getConnection() throws SQLException {
        if (url == null || user == null || password == null) {
            throw new IllegalStateException("Database credentials are not set in environment variables.");
        }
        return DriverManager.getConnection(url, user, password);
    }
}
