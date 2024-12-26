package org.example.project.service.notifications;

public interface OuthService {
    String getAccessToken(String authorizationCode, String codeVerifier);
}
