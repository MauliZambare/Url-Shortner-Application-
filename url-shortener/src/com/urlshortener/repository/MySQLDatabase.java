package com.urlshortener.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/urlshortener_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // 🔐 Change if different

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to database: " + URL);
            return connection;
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
