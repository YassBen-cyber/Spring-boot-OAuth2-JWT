package com.example.SecureAuth.controller;

import com.example.SecureAuth.model.User;
import com.example.SecureAuth.repository.UserRepository;
import com.example.SecureAuth.security.JwtService;

import jakarta.servlet.http.HttpServletResponse;

import com.example.SecureAuth.dto.LoginRequest;
import com.example.SecureAuth.dto.JwtResponse;
import com.example.SecureAuth.dto.ErrorResponse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, HttpServletResponse resp) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setProvider("local");
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        String jwt = jwtService.generateToken(savedUser.getEmail());

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true).secure(true).sameSite("Lax").path("/").maxAge(86400).build();
        resp.addHeader("Set-Cookie", jwtCookie.toString());

        return ResponseEntity.ok().body(new JwtResponse(jwt, savedUser.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse resp) {
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with email: " + loginRequest.getEmail()));

            boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            if (!passwordMatch) {
                throw new BadCredentialsException("Invalid password");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateToken(user.getEmail());

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true).secure(true).sameSite("Lax").path("/").maxAge(86400).build();
            resp.addHeader("Set-Cookie", jwtCookie.toString());

            return ResponseEntity.ok().body(new JwtResponse(jwt, user.getEmail()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Authentication failed", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse resp) {
        ResponseCookie deleteJwt = ResponseCookie.from("jwt", "")
                .httpOnly(true).secure(true).sameSite("Lax").path("/").maxAge(0).build();

        resp.addHeader("Set-Cookie", deleteJwt.toString());

        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
