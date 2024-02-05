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
        System.out.println(jwkSetJson);
        
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg1ZTU1MTA3NDY2YjdlMjk4MzYxOTljNThjNzU4MWY1YjkyM2JlNDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMDU5MjMxMTY3NjU0LWNxNjNwazdzc2VndGI5NnJuMHRvcDdiYmkwZDVyNWQ3LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMTA1OTIzMTE2NzY1NC1jcTYzcGs3c3NlZ3RiOTZybjB0b3A3YmJpMGQ1cjVkNy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwOTU4OTQ5ODg4MDMyMjcyMzUwNiIsImVtYWlsIjoic2FtYXJhYWxtZWlkYTM3OUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmJmIjoxNzA3MTAzNjcwLCJuYW1lIjoiU2FtYXJhIEFsbWVpZGEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jSnMwano5bXBNN3AyX0RLZ0txVVRKc1ZVRFJ1b09sZDdvY0VxaVNwOW5nQmxPWD1zOTYtYyIsImdpdmVuX25hbWUiOiJTYW1hcmEiLCJmYW1pbHlfbmFtZSI6IkFsbWVpZGEiLCJsb2NhbGUiOiJwdC1CUiIsImlhdCI6MTcwNzEwMzk3MCwiZXhwIjoxNzA3MTA3NTcwLCJqdGkiOiI3YWE4YjRlYjhmNzRlNDE0OTg1MDQ5ZjA3MTRlMDk1MzdjODU4Y2ZmIn0.d9sc638oZ78m3La4I_rwIlhhgLYGEMll-Puy7kXG5ONsFjwjYbd3bH0OyFSdJcWGkYoHzHgSXYP8ATgBAJhDOvzsa5L-QEcWIyzmOlJ9cd_6-hxoCI-oB24_gL-ywRNBxCeEzoqOFTj7CyF9mgG54hKYz-qy_N7KQ-WC8AWN3ghmEJMoAWDjws3uALvTI5oU0LkUzl4vqt1GDQWi3YFIuqtkwByRzzDf1ZTx8Px_SzqZL9f6RHI2og7QtfhIAzBImcKk08wmxsjIcW0Vj91P85R1RGVAQa-Ne82SOS_z7LpoS-dEBvK-lgl7RSsgvPJEOG3LBFBYcp1A7Y7rAerkmg";

        // Verifique a assinatura do token
        if (tokenVerifier.verifyGoogleIdToken(idToken, jwkSetJson)) {
            // Token válido, faça o que for necessário
            System.out.println("Token válido: " + idToken);
            return "Token válido!";
        } else {
            // Token inválido
            System.out.println("Token inválido: " + idToken);
            return "Token inválido!";
        }
    }
}
