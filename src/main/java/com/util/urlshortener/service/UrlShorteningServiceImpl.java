package com.util.urlshortener.service;

import com.util.urlshortener.config.AppConfig;
import com.util.urlshortener.util.Base62Util;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private AppConfig appConfig;

    private final Map<String, String> urlStorage = new ConcurrentHashMap<>();
    private final Map<String, String> reverseUrlStorage = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public UrlShorteningServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public String encode(String originalUrl) {
        // Check if the original URL is already encoded
        if (reverseUrlStorage.containsKey(originalUrl)) {
            return appConfig.getBaseUrl() + reverseUrlStorage.get(originalUrl);
        }

        int maxRetries = appConfig.getEncodeRetries();
        int attempts = 0;
    
        String shortenedUrl;

        // give maxRetries if shortenedUrl is generated duplicate
        do {
            long id = counter.incrementAndGet();
            shortenedUrl = Base62Util.encode(id, appConfig.getShortUrlLength());
            attempts++;
        } while (urlStorage.containsKey(shortenedUrl) && attempts < maxRetries);
    
        if (urlStorage.containsKey(shortenedUrl)) {
            throw new RuntimeException("Failed to generate a unique short URL after " + maxRetries + " attempts");
        }

        synchronized(this) {
            if (urlStorage.putIfAbsent(shortenedUrl, originalUrl) != null) {
                throw new RuntimeException("Failed to generate a unique short URL, already exists: shortenedUrl="
                    + shortenedUrl + ", originalUrl=" + originalUrl);
            }
            reverseUrlStorage.putIfAbsent(originalUrl, shortenedUrl);
        }

        return appConfig.getBaseUrl() + shortenedUrl;
    }

    @Override
    public String decode(String shortenedUrl) {
        return urlStorage.get(shortenedUrl.replace(appConfig.getBaseUrl(), ""));
    }
}
