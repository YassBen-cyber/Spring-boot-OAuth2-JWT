package com.example.SecureAuth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SecureAuth.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/email")
    String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
