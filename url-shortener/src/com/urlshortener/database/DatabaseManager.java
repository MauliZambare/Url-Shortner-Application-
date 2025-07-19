package com.urlshortener.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {
    private static Connection connection;

    public static void connect() {
        try {
            // Load H2 driver
            Class.forName("org.h2.Driver");

            // Connect to H2 in-memory database (or file-based for persistence)
            connection = DriverManager.getConnection("jdbc:h2:~/urlshortenerdb", "sa", "");

            System.out.println("✅ Connected to H2 database");

            // Create table if it doesn't exist
            connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS url_mapping (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "short_code VARCHAR(255) UNIQUE NOT NULL, " +
                "original_url TEXT NOT NULL" +
                ")"
            );
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
