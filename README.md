🔐 OAuth2 Google + JWT (Spring boot)

Ce projet est une petite démonstration d’un système d’authentification moderne combinant Google OAuth 2.0 et JWT. Il inclut un back end sécurisé et un front end minimaliste permettant aux utilisateurs de se connecter avec leur compte Google, puis d’interagir avec une API protégée.

✨ Fonctionnalités

Connexion avec Google OAuth

Génération et validation d’un JWT

Routes API sécurisées via Bearer Token

Front end simple permettant :

Connexion avec Google

Affichage des infos utilisateur après connexion

Gestion automatique de la session côté client

🏗️ Stack Technique
Côté	Technologies
Back end	Spring Boot 
Auth	Google OAuth 2.0 + JWT
Front end	React

npm start



🧠 Architecture Simplifiée
[Client Web]  →  /auth/google  →  [Server + Google OAuth]
        ↘ JWT reçu ✅   →  /api/protected route

✅ Objectif du Projet

Ce projet montre comment mettre en place un système d’auth moderne dans un environnement professionnel.
