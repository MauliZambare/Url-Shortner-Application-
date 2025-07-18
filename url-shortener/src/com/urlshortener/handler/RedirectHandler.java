package com.urlshortener.handler;

import com.urlshortener.service.UrlShortenerService;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RedirectHandler implements HttpHandler {
    private final UrlShortenerService service;

    public RedirectHandler(UrlShortenerService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();  // /abc123
        String shortCode = path.substring(1);              // remove leading '/'

        String originalUrl = service.getOriginalUrl(shortCode);

        if (originalUrl != null) {
            exchange.getResponseHeaders().add("Location", originalUrl);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_TEMP, -1);
        } else {
            String notFoundMsg = "Short URL not found";
            byte[] response = notFoundMsg.getBytes();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length);
            exchange.getResponseBody().write(response);
        }

        exchange.getResponseBody().close();
    }
}
