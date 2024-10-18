package org.example.project.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;

@RestController

@RequiredArgsConstructor

@RequestMapping("/api/auth/oauth2")
public class OAuth2Controller {
//        @Autowired
//        private final AuthenticationService service;

//        @PostMapping("/register")
//        public ResponseEntity<AuthenticationResponse> register(
//                @RequestBody RegisterRequest request
//        ) {
//            return ResponseEntity.ok(service.register(request));
//        }
//        @PostMapping("/authenticate")
//        public ResponseEntity<AuthenticationResponse> authenticate(
//                @RequestBody AuthenticationRequest request
//        ) {
//            return ResponseEntity.ok(service.authenticate(request));
//        }
//
//        @PostMapping("/refresh-token")
//        public void refreshToken(
//                HttpServletRequest request,
//                HttpServletResponse response
//        ) throws IOException {
//            service.refreshToken(request, response);
//        }
//        @GetMapping("/profile")
//        public String profile(OAuth2AuthenticationToken token, Model model) {
//            model.addAttribute("name", token.getPrincipal().getAttribute("name"));
//            model.addAttribute("email", token.getPrincipal().getAttribute("email"));
//            model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));
//            return "user-profile";
//        }
//
//        @GetMapping("/login")
//        public String login() {
//            return "custom_login";
//        }

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));
        return "user-profile";
    }

    @GetMapping("/login")
    public String login() {
        return "custom_login";
    }
//     @RequestMapping("/")
//        public String home() {
//            return "Welcome!";
//        }
//
//        @RequestMapping("/user")
//        public Principal user(Principal user) {
//            return user;
//        }

    }



