package com.urlshortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.service.UrlShortenerService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class RedirectHandler implements HttpHandler {
    private final UrlShortenerService service;

    public RedirectHandler(UrlShortenerService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println("RedirectHandler: Request path: " + path);

        if ("/".equals(path)) {
            // Serve index.html
            File file = new File("src/index.html");
            exchange.sendResponseHeaders(200, file.length());
            try (OutputStream os = exchange.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        } else {
            String shortCode = path.substring(1);
            System.out.println("RedirectHandler: Extracted short code: " + shortCode);
            String originalUrl = service.getOriginalUrl(shortCode);
            System.out.println("RedirectHandler: Retrieved original URL: " + originalUrl);

            if (originalUrl != null) {
                exchange.getResponseHeaders().set("Location", originalUrl);
                exchange.sendResponseHeaders(302, -1); // 302 Found
            } else {
                String response = "URL not found";
                exchange.sendResponseHeaders(404, response.length());
                exchange.getResponseBody().write(response.getBytes());
            }
        }
        exchange.close();
    }
}