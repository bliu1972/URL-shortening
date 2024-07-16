package com.util.urlshortener.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.util.urlshortener.config.AppConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningServiceImplTest {
    private static final String ORIGINAL_URL = "https://yahoo.com";
    private static final String EXPECTED_ORIGINAL_URL = ORIGINAL_URL;
    private static final String SHORTENED_URL_BASE = "https://myshort.url/";
    private static final String SHORTENED_URL = SHORTENED_URL_BASE + "AAAAAB";
    private static final String EXPECTED_SHORTENED_URL = SHORTENED_URL;
    private static final String NON_EXISTING_SHORTENED_URL = SHORTENED_URL_BASE + "nonExistent";

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private UrlShorteningServiceImpl urlShorteningService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking the AppConfig responses
        when(appConfig.getBaseUrl()).thenReturn(SHORTENED_URL_BASE);
        when(appConfig.getShortUrlLength()).thenReturn(6);
    }

    @Test
    public void testEncode() {
        // Get shortened URL
        String shortenedUrl = urlShorteningService.encode(ORIGINAL_URL);

        // Assert if expected shortened URL is the same as actual shortened URL
        assertEquals(EXPECTED_SHORTENED_URL, shortenedUrl);
    }

    @Test
    public void testDecode() {
        // Encode the URL first
        String shortenedUrl = urlShorteningService.encode(ORIGINAL_URL);

        // Get decoded URL
        String decodedUrl = urlShorteningService.decode(shortenedUrl);

        // Assert if the expected original URL is the same as actual decoded URL
        assertEquals(EXPECTED_ORIGINAL_URL, decodedUrl);
    }

    @Test
    public void testDecodeNonExistentShortenedUrl() {
        // Encode some URL first
        urlShorteningService.encode(ORIGINAL_URL);

        // Get decoded URL
        String decodedUrl = urlShorteningService.decode(NON_EXISTING_SHORTENED_URL);

        // Assert decoded URL is null
        assertNull(decodedUrl);
    }
}
