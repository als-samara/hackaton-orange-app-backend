package com.orange.orangeportfolio.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Data
public class GooglePublicKeyProvider {
	
	public String getGooglePublicKeys() {
        String url = "https://www.googleapis.com/oauth2/v3/certs";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
