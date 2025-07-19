package com.urlshortener.repository;

import com.urlshortener.model.UrlMapping;

import java.sql.*;

public class UrlRepository {

    public void save(UrlMapping mapping) {
        String query = "INSERT INTO url_mapping (short_url, original_url) VALUES (?, ?)";

        try (Connection conn = MySQLDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mapping.getShortCode());
            stmt.setString(2, mapping.getOriginalUrl());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Failed to save URL mapping");
            e.printStackTrace();
        }
    }

    public UrlMapping findByShortCode(String shortCode) {
        String query = "SELECT original_url FROM url_mapping WHERE short_url = ?";

        try (Connection conn = MySQLDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, shortCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String originalUrl = rs.getString("original_url");
                return new UrlMapping(originalUrl, shortCode);
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to retrieve URL mapping");
            e.printStackTrace();
        }
        return null;
    }
}
