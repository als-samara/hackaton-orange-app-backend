package com.orange.orangeportfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.orangeportfolio.client.GoogleLoginClient;
import com.orange.orangeportfolio.model.GoogleLoginUser;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class TokenController {
	
	private final GoogleLoginClient googleLoginClient;

    public TokenController(GoogleLoginClient googleLoginClient) {
        this.googleLoginClient = googleLoginClient;
    }

    @PostMapping("/logingoogle")
    public ResponseEntity<String> handleTokenSignIn(@RequestBody String idToken) {
        try {
            // Chame o cliente GoogleLogin para verificar e manipular o token
            GoogleLoginUser newUser = googleLoginClient.verifyAndHandleGoogleLogin(idToken);

            if (newUser != null) {
                // Usuário verificado ou criado com sucesso
                return ResponseEntity.ok("OK");
            } else {
                // Algo deu errado durante a verificação ou manipulação do token
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
            }
        } catch (Exception e) {
            // Log de exceções para depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
        }
    }
}
