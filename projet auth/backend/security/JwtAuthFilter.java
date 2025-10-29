package com.example.SecureAuth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {

        // Récupère le cookie "jwt"
        Cookie jwtCookie = request.getCookies() != null ? Arrays.stream(request.getCookies())
                .filter(c -> "jwt".equals(c.getName()))
                .findFirst()
                .orElse(null) : null;
        System.out.println("JWT Cookie: " + (jwtCookie != null ? jwtCookie.getValue() : "null"));

        // Valide le token et configure le contexte de sécurité
        if (jwtCookie != null) {
            String token = jwtCookie.getValue();
            String username = jwtService.getEmailFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
