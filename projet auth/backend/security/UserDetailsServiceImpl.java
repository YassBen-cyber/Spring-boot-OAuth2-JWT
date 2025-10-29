package com.example.SecureAuth.security;

import com.example.SecureAuth.model.User;
import com.example.SecureAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        String password = user.getPassword();
        if (password == null) {
            // Pour les utilisateurs OAuth2, mettre un mot de passe "dummy" non utilisé
            password = "";
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(password)
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .accountExpired(!user.isEnabled())
                .accountLocked(!user.isEnabled())
                .credentialsExpired(!user.isEnabled())
                .disabled(!user.isEnabled())
                .build();
    }
}
