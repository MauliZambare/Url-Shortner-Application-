package com.urlshortener;

import com.sun.net.httpserver.HttpServer;
import com.urlshortener.handler.ShortenUrlHandler;
import com.urlshortener.handler.RedirectHandler;
import com.urlshortener.service.UrlShortenerService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        UrlShortenerService service = new UrlShortenerService();

        server.createContext("/shorten", new ShortenUrlHandler(service));
        server.createContext("/", new RedirectHandler(service));

        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("Server started on port 8000");
    }
}
