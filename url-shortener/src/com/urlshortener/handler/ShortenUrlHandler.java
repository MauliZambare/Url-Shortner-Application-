package com.urlshortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.service.UrlShortenerService;
import org.json.JSONObject;

import java.io.*;

public class ShortenUrlHandler implements HttpHandler {
    private final UrlShortenerService service;

    public ShortenUrlHandler(UrlShortenerService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Only accept POST requests
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        // Set response headers (optional: for browser or CORS support)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        // Read request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        try {
            JSONObject json = new JSONObject(jsonBody.toString());
            String originalUrl = json.getString("originalUrl");

            if (originalUrl == null || originalUrl.trim().isEmpty()) {
                sendResponse(exchange, 400, "{\"error\":\"URL cannot be empty\"}");
                return;
            }

            String shortUrl = service.shortenUrl(originalUrl.trim());

            // Respond with short URL in JSON
            JSONObject responseJson = new JSONObject();
            responseJson.put("shortUrl", shortUrl);

            sendResponse(exchange, 200, responseJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "{\"error\":\"Invalid JSON format or missing 'originalUrl'\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] responseBytes = responseText.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
