package com.example.SecureAuth.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private final String jwtSecret = "IMesriS9VAw0O5RS8hRRF8v2zGNSDG5ZpjiAwnmdEOmbzZyVIFQDXrQU_TkaSR4LKenf1hRwuIJsU6-q00TqaQ";
    private final long jwtExpirationMs = 86400000; // 1 jour

    public String generateToken(String email) {
        System.out.println("Appel generateToken avec email: " + email);
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
        System.out.println("Token généré: " + token);
        return token;
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
