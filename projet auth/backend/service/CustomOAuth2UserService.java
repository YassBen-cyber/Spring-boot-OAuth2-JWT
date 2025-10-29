package com.example.SecureAuth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.SecureAuth.model.User;
import com.example.SecureAuth.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // Récupère les infos Google
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        // Vérifie si l’utilisateur existe en BDD
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // Si pas trouvé → on peut l’enregistrer automatiquement
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setEnabled(true);
                    newUser.setProvider("google");
                    return userRepository.save(newUser);
                });

        return oAuth2User;
    }
}
