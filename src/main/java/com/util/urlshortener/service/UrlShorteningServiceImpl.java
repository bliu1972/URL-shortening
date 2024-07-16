package com.util.urlshortener.service;

import com.util.urlshortener.config.AppConfig;
import com.util.urlshortener.util.Base62Util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private AppConfig appConfig;

    private final Map<String, String> urlStorage = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public UrlShorteningServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public String encode(String originalUrl) {
        int maxRetries = appConfig.getEncodeRetries();
        int attempts = 0;
    
        String shortenedUrl;

        do {
            long id = counter.incrementAndGet();
            shortenedUrl = Base62Util.encode(id, appConfig.getShortUrlLength());
            attempts++;
        } while (urlStorage.containsKey(shortenedUrl) && attempts < maxRetries);
    
        if (urlStorage.containsKey(shortenedUrl)) {
            throw new RuntimeException("Failed to generate a unique short URL after " + maxRetries + " attempts");
        }

        urlStorage.put(shortenedUrl, originalUrl);
        return appConfig.getBaseUrl() + shortenedUrl;
    }

    @Override
    public String decode(String shortenedUrl) {
        return urlStorage.get(shortenedUrl.replace(appConfig.getBaseUrl(), ""));
    }
}
