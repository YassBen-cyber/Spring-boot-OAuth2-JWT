package com.example.SecureAuth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test/public")
    public String publicEndpoint() {
        return "Public content";
    }

    @GetMapping("/api/test/private")
    public String privateEndpoint() {
        return "Private content - only for authenticated users";
    }
}
