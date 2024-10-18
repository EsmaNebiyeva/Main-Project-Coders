package org.example.project.security.auth;

import jakarta.mail.MessagingException;

public interface MailService {
    String sendMail(String email);
    String sendMultiMediaMail(String email) throws MessagingException;
}
