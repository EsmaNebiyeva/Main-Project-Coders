package org.example.project.security.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.project.security.user.UserDetail;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//
//@Configuration
//public class OAuth2ClientConfig {
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        // You can use this to configure multiple OAuth2 providers like Google, GitHub, etc.
//        return new InMemoryClientRegistrationRepository();
//    }
//}
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class OAuth2ClientConfig extends UsernamePasswordAuthenticationFilter {
//
//    private final JwtService jwtUtil;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // Google'dan gelen kullanıcının bilgilerini al
//        String username = principal.getAttribute("email");
//
//        // JWT oluştur
//        String token = jwtUtil.generateToken(new UserDetail(username));
//
//        // JWT'yi döndür
//        response.setHeader("Authorization", "Bearer " + token);

//        return super.attemptAuthentication(request, response);
//    }
//}