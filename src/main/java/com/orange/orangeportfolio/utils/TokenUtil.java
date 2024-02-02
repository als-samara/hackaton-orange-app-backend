package com.orange.orangeportfolio.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenUtil {

    public static String extractUserEmailFromToken(String token) {
        // Supondo que o token seja um JWT no formato "Bearer <token>"
        String cleanedToken = token.replace("Bearer ", "");

        // Chave secreta para verificar a assinatura do token
        String secretKey = "suaChaveSecreta";

        // Decodificar o token e obter as reivindicações (claims)
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(cleanedToken).getBody();

        // Extrair o e-mail do campo "sub" (sujeito) do token
        return claims.getSubject();
    }
}
