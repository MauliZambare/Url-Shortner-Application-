package com.urlshortener.service;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.util.UrlGenerator;

public class UrlShortenerService {
    private final UrlRepository repository = new UrlRepository();

    public String shortenUrl(String originalUrl) {
        String code = UrlGenerator.generateShortCode();
        UrlMapping mapping = new UrlMapping(code, originalUrl);
        repository.save(mapping);
        return "http://localhost:8080/" + code;
    }

    public String getOriginalUrl(String shortCode) {
        UrlMapping mapping = repository.findByShortCode(shortCode);
        return (mapping != null) ? mapping.getOriginalUrl() : null;
    }
}
