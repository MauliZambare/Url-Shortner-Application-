package com.urlshortener.handler;

import com.urlshortener.service.UrlShortenerService;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ShortenUrlHandler implements HttpHandler {
    private final UrlShortenerService service;

    public ShortenUrlHandler(UrlShortenerService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
            return;
        }

        // Read request body (URL in plain text)
        InputStream is = exchange.getRequestBody();
        String originalUrl = new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();

        String shortUrl = service.shortenUrl(originalUrl);

        byte[] response = shortUrl.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }
}
