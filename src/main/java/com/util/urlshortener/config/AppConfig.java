package com.util.urlshortener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.short-url-length}")
    private int shortUrlLength;

    @Value("${app.encode-retries}")
    private int encodeRetries;

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getShortUrlLength() {
        return shortUrlLength;
    }

    public int getEncodeRetries() {
        return encodeRetries;
    }
}
