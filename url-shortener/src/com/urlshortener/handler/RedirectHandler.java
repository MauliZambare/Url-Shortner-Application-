package com.urlshortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.service.UrlShortenerService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RedirectHandler implements HttpHandler {

    private final UrlShortenerService service;

    public RedirectHandler(UrlShortenerService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String shortCode = path.substring(1); // remove leading slash

        String originalUrl = service.getOriginalUrl(shortCode);
        if (originalUrl != null) {
            exchange.getResponseHeaders().set("Location", originalUrl);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_TEMP, -1);
        } else {
            String response = "404 - File Not Found";
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
