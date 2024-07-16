package com.util.urlshortener.service;

public interface UrlShorteningService {
    String encode(String originalUrl);
    String decode(String shortenedUrl);
}
