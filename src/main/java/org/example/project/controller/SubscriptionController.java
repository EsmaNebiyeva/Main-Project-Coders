package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.subscribetion.Subscription;
import org.example.project.model.SubscriptionDTO;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.subscribetion.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/"})
public class SubscriptionController {

    @Autowired
    private final SubscriptionService subscriptionService;
    @Autowired
    private  final JwtService jwtService;
    @Autowired
    private  final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<SubscriptionDTO> createSubscription(HttpServletRequest request, @RequestBody Subscription subscription) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            UserDetail user = userService.findByEmail(email);
            if(user != null) {
                subscription.setUserEmail(email);
                SubscriptionDTO subscriptionDTO = subscriptionService.saveSubscription(subscription);
                return new ResponseEntity<>(subscriptionDTO, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionByEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            List<SubscriptionDTO> subscriptionsByUserId = subscriptionService.getSubscriptionsByUserId(email);
                return new ResponseEntity<>(subscriptionsByUserId, HttpStatus.OK);

        } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/cancel")
    public ResponseEntity<List<SubscriptionDTO>> cancelSubscription(HttpServletRequest request,@RequestBody List<SubscriptionDTO> subscriptions) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            List<SubscriptionDTO> subscriptionsByUserId = subscriptionService.cancelSubscription(email, subscriptions);
            return new ResponseEntity<>(subscriptionsByUserId, HttpStatus.OK);
        } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}