package com.urlshortener.repository;

import com.urlshortener.model.ShortUrl;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUrlRepository {
    private final Map<String, ShortUrl> store = new HashMap<>();

    public void save(ShortUrl url) {
        store.put(url.getShortUrl(), url);
    }

    public ShortUrl findByShortUrl(String shortUrl) {
        return store.get(shortUrl);
    }
}
