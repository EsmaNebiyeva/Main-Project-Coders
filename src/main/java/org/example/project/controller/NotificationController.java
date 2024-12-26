package org.example.project.controller;

import java.util.List;

import org.example.project.model.NotificationDto;

import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.notifications.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


@RestController
@Controller
@RequestMapping("/api/not")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.vercel.app/"})
public class NotificationController {
    @Autowired
    private final NotificationService notificationService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    
    // @PostMapping("/")
    // public ResponseEntity<String> sendNotification(HttpServletRequest request) {
    //     notificationService.sendNotification(userId, message);
    //     return ResponseEntity.ok("Bildiriş göndərildi: " + message);
    // }
    
    
    @GetMapping("/get")
    public ResponseEntity<List<NotificationDto>> getNResponseEntity(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

        // try {
        //     if (jwtService.isJwt(token)) { // JWT mi kontrol edilir.
        //         email = jwtUtil.extractEmail(token); // JWT'den email çıkar.
        //     } else if (isGoogleToken(token)) { // Google token mi kontrol edilir.
        //         email = googleTokenUtil.extractEmailFromGoogleToken(token); // Google'dan email doğrula.
        //     } else {
        //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Geçersiz token.
        //     }
        // }
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                List<NotificationDto> notification = notificationService.getNotification(email);
                return new ResponseEntity<>(notification,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
