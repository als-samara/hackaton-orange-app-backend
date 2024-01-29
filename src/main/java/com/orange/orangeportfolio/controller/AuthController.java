package com.orange.orangeportfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/token")
    public ResponseEntity<String> receberToken(@AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken) {
        // Obtém as informações do usuário do token de autenticação OAuth2
        String email = authenticationToken.getPrincipal().getAttribute("email");

        // Você pode adicionar mais informações conforme necessário
        String nome = authenticationToken.getPrincipal().getAttribute("name");

        // Faça as ações necessárias com as informações do usuário, como salvar no banco de dados
        // ...

        // Retorne uma resposta adequada
        return ResponseEntity.ok("Token recebido com sucesso");
    }
}
