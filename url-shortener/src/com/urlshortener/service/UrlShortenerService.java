package com.urlshortener.service;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.util.UrlGenerator;

public class UrlShortenerService {
    private final UrlRepository repository;

    // Constructor injection for better testing and flexibility
    public UrlShortenerService() {
        this.repository = new UrlRepository();
    }

    public String shortenUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Original URL cannot be null or empty");
        }

        String shortCode = UrlGenerator.generateShortCode();
        UrlMapping mapping = new UrlMapping(shortCode, originalUrl);
        repository.save(mapping);

        return "http://localhost:8080/" + shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        if (shortCode == null || shortCode.trim().isEmpty()) {
            return null;
        }

        UrlMapping mapping = repository.findByShortCode(shortCode);
        return (mapping != null) ? mapping.getOriginalUrl() : null;
    }
}
