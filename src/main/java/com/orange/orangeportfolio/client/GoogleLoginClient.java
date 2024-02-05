package com.orange.orangeportfolio.client;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.orange.orangeportfolio.model.GoogleLoginUser;
import com.orange.orangeportfolio.model.GoogleLoginUserRepository;
import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.UserRepository;

public class GoogleLoginClient {

    private static final String CLIENT_ID = "1059231167654-cq63pk7ssegtb96rn0top7bbi0d5r5d7.apps.googleusercontent.com";

    private UserRepository userRepository;
    private GoogleLoginUserRepository googleLoginUserRepository;

    public GoogleLoginClient(UserRepository userRepository, GoogleLoginUserRepository googleLoginUserRepository) {
        this.userRepository = userRepository;
        this.googleLoginUserRepository = googleLoginUserRepository;
    }

    public GoogleLoginUser verifyAndHandleGoogleLogin(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    null)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");

                if (userExistsInDatabase(email)) {
                    establishAuthenticatedSession(email);
                } else {
                    createUserInDatabase(name, email, pictureUrl);
                    establishAuthenticatedSession(email);
                    // Agora, você pode enviar o token de volta ao frontend, se necessário
                }

                // Crie um novo objeto GoogleLoginUser e atribua os valores
                GoogleLoginUser googleLoginUser = new GoogleLoginUser();
                googleLoginUser.setName(name);
                googleLoginUser.setEmail(email);
                googleLoginUser.setProfilePicture(pictureUrl);

                return googleLoginUser;
            } else {
                System.out.println("Invalid ID token.");
                return null;
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean userExistsInDatabase(String userEmail) {
        Optional<User> regularUser = userRepository.findAllByEmail(userEmail);
        Optional<GoogleLoginUser> googleUser = googleLoginUserRepository.findByEmail(userEmail);

        return regularUser.isPresent() || googleUser.isPresent();
    }

    private void createUserInDatabase(String userName, String userEmail, String userPictureUrl) {
    	GoogleLoginUser googleLoginUser = new GoogleLoginUser();
        googleLoginUser.setName(userName);
        googleLoginUser.setEmail(userEmail);
        googleLoginUser.setProfilePicture(userPictureUrl);
    }

    private String establishAuthenticatedSession(String userEmail) {
        try {
            // Lógica para estabelecer uma sessão autenticada
            // Implemente de acordo com o mecanismo de autenticação do seu aplicativo

            // Aqui, estamos gerando um token JWT simples como exemplo
            String secretKey = "seu_secreto_aqui";  // Substitua pelo seu segredo real
            return Jwts.builder()
                    .setSubject(userEmail)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
