package com.urlshortener.repository;

import com.urlshortener.database.DatabaseManager;
import com.urlshortener.model.UrlMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UrlMappingRepository {

    public void save(UrlMapping mapping) {
        String sql = "INSERT INTO url_mapping (short_code, original_url) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mapping.getShortCode());
            stmt.setString(2, mapping.getOriginalUrl());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("❌ Error saving URL mapping: " + e.getMessage());
        }
    }

    public String findOriginalUrlByShortCode(String shortCode) {
        String sql = "SELECT original_url FROM url_mapping WHERE short_code = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shortCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("original_url");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error retrieving original URL: " + e.getMessage());
        }

        return null;
    }
}
