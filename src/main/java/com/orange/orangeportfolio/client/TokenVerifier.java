package com.orange.orangeportfolio.client;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

@Component
public class TokenVerifier {

    public boolean verifyGoogleIdToken(String idToken, String jwkSetJson) throws JOSEException {
        try {
            // Parse the JWK set
            JWKSet jwkSet = JWKSet.parse(jwkSetJson);

            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(idToken);

            // Get the JWK used to sign the token
            RSAKey rsaKey = (RSAKey) jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());

            // Create a JWS verifier using the public RSA key
            JWSVerifier verifier = new RSASSAVerifier(rsaKey);

            // Verify the signature
            return signedJWT.verify(verifier);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
