package org.example.project.service.notifications.impl;

import org.example.project.service.notifications.OuthService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OuthServiceImpl implements OuthService{

    // @Value("${google.client.id}")
    private String clientId="638587205179-si0t0bghoqtn4bsbcjljusnemggpr0an.apps.googleusercontent.com";

    // @Value("${google.client.secret}")
    private String clientSecret="";

    // @Value("${google.redirect.uri}")
    private String redirectUri="https://authjs-next.vercel.app/api/auth/callback/google";

    public String getAccessToken(String authorizationCode, String codeVerifier) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        // Token talebi için gerekli parametreler
        String body = "code=" + authorizationCode +
                      "&client_id=" + clientId +
                      "&client_secret=" + clientSecret +
                      "&redirect_uri=" + redirectUri +
                      "&grant_type=authorization_code" +
                      "&code_verifier=" + codeVerifier;  // PKCE için

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // RestTemplate kullanarak token isteği gönderme
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);

        // Yanıtı (access_token) JSON'dan çıkartma
        String accessToken = extractAccessToken(response.getBody());
        return "Bearer "+ accessToken;
    }

    private String extractAccessToken(String responseBody) {
        // JSON parsing yaparak access_token'ı çıkartmak
        // Yanıt, {"access_token": "...", "expires_in": 3600, ...} şeklinde olacaktır
        // JSON parsing işlemini burada gerçekleştirebilirsiniz
        return responseBody; // JSON'dan "access_token" değerini çıkartın
    }
}

