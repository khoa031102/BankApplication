package com.example.ebanking.endpoint.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FaviconController {

    @GetMapping("/favicon")
    public ResponseEntity<byte[]> getFavicon() {
        try {
            Resource resource = new ClassPathResource("static/favicon.ico");
            Path path = Paths.get(resource.getFile().getPath());
            return ResponseEntity.ok(Files.readAllBytes(path));
        } catch (IOException ignored) {
            throw new RuntimeException();
        }
    }
}
