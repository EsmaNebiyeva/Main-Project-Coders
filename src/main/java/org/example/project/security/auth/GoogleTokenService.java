package org.example.project.security.auth;

import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.google.api.client.http.javanet.NetHttpTransport;


import com.google.api.client.json.jackson2.JacksonFactory;

import io.jsonwebtoken.io.IOException;
@Service
public class GoogleTokenService {
    
    private String googleClientId="52453221517-mdafljjbtv0rulnbn61mcqun51v2cfma.apps.googleusercontent.com";

    public GoogleIdToken.Payload verifyToken(String idToken) throws Exception {
        try {
            // Google'ın OAuth2 Token doğrulama API'sine istek gönderme
            GoogleIdToken.Payload payload = verifyGoogleIdToken(idToken);

            // Buradan kullanıcı bilgilerini çıkartabiliriz
            // String email = payload.getEmail();
            // String username = payload.getSubject();  // Kullanıcı ID'si veya kullanıcı adı

            // Burada token'dan elde edilen kullanıcı bilgilerini döndürebiliriz
            return payload;
        } catch ( GeneralSecurityException|IOException e) {
            throw new Exception("Invalid Google ID Token", e);
        }
    }

    private GoogleIdToken.Payload verifyGoogleIdToken(String idToken) throws  IOException, GeneralSecurityException,Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
            .setAudience(Collections.singletonList(googleClientId)) // Uygulamanın client id'si
            .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        if (googleIdToken != null) {
            return googleIdToken.getPayload();
        } else {
            throw new Exception("Invalid ID token.");
        }
    }
    public String extractEmailFromGoogleToken(String token) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), 
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList("YOUR_CLIENT_ID")) // Google Client ID'nizi buraya koyun
                    .build();
    
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                return payload.getEmail(); // E-posta'yı döndürür
            } else {
                throw new IllegalArgumentException("Geçersiz token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Token doğrulama hatası: " + e.getMessage());
        }
    }
//     private boolean isGoogleToken(String token) {
//     try {
//         GoogleIdToken.Payload.verifyGoogleIdToken(token); // Google token doğrulama metodu.
//         return true;
//     } catch (Exception e) {
//         return false; // Google token değilse ya da geçersizse.
//     }
// }
}
