package com.urlshortener;

import com.sun.net.httpserver.HttpServer;
import com.urlshortener.handler.ShortenUrlHandler;
import com.urlshortener.service.UrlShortenerService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class UrlShortenerServer {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        UrlShortenerService service = new UrlShortenerService();
        server.createContext("/shorten", new ShortenUrlHandler(service));

        System.out.println("Server started on http://localhost:" + port);
        server.setExecutor(null);
        server.start();
    }
}
