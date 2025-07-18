package com.urlshortener.model;

public class ShortUrl {
    private String originalUrl;
    private String shortUrl;
    private long createdAt;

    public ShortUrl(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = System.currentTimeMillis();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
