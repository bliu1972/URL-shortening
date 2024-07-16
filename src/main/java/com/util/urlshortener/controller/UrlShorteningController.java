package com.util.urlshortener.controller;

import com.util.urlshortener.dto.EncodeRequest;
import com.util.urlshortener.dto.UrlResponse;
import com.util.urlshortener.service.UrlShorteningService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UrlShorteningController {
    private final UrlShorteningService urlShorteningService;

    public UrlShorteningController(UrlShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
    }

    @PostMapping("/encode")
    public ResponseEntity<UrlResponse> encode(@RequestBody EncodeRequest request) {
        String originalUrl = request.getOriginalUrl();
        if (originalUrl == null || originalUrl.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String shortenedUrl = urlShorteningService.encode(originalUrl);
        UrlResponse res = new UrlResponse(originalUrl, shortenedUrl);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/decode")
    public ResponseEntity<UrlResponse> decode(@RequestParam String shortenedUrl) {
        String originalUrl = urlShorteningService.decode(shortenedUrl);
        if (originalUrl == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UrlResponse res = new UrlResponse(originalUrl, shortenedUrl);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
