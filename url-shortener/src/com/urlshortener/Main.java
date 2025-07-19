package com.urlshortener;

import com.sun.net.httpserver.HttpServer;
import com.urlshortener.handler.IndexHandler;
import com.urlshortener.handler.ShortenUrlHandler;
import com.urlshortener.service.UrlShortenerService;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve index.html
        server.createContext("/", new IndexHandler());

        // Add this line for URL shortening endpoint
        UrlShortenerService service = new UrlShortenerService();
        server.createContext("/shorten", new ShortenUrlHandler(service));

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started at http://localhost:8080/");
    }
}
