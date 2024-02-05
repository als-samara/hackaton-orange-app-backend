package com.orange.orangeportfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.orange.orangeportfolio.client.GooglePublicKeyProvider;
import com.orange.orangeportfolio.client.TokenVerifier;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController("/token")
public class TokenController {
	
	@Autowired
	TokenVerifier tokenVerifier;
	
	@Autowired
	GooglePublicKeyProvider googlePublicKeyProvider;
	
	@PostMapping("/login/oauth2")
    public String handleTokenSignIn(@RequestBody String idToken) throws JOSEException {
		// Obtenha as chaves públicas do Google
        String jwkSetJson = googlePublicKeyProvider.getGooglePublicKeys();
        
        // Verifique a assinatura do token
        if (tokenVerifier.verifyGoogleIdToken(idToken, jwkSetJson)) {
            
            return idToken;
        } else {
            return "Token inválido!";
        }
    }
}
