package com.urlshortener.repository;

import com.urlshortener.model.UrlMapping;

import java.util.HashMap;
import java.util.Map;

public class UrlRepository {
    private final Map<String, UrlMapping> urlStore = new HashMap<>();

    public void save(UrlMapping mapping) {
        urlStore.put(mapping.getShortCode(), mapping);
    }

    public UrlMapping findByShortCode(String shortCode) {
        return urlStore.get(shortCode);
    }
}
