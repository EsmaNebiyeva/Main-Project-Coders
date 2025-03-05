package org.example.project.controller;


import org.example.project.entity.subscribetion.Payment;
import org.example.project.security.auth.MailService;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.ConfirmationService;
import org.example.project.service.subscribetion.PaymentServicr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/","https://posive.vercel.app/"})
public class PaymentController {
     @Autowired
    private final MailService mailService;
@Autowired
private final ConfirmationService confirmationService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PaymentServicr paymentServicr;
    @PostMapping("/find")
    public ResponseEntity<String>  getPayment(HttpServletRequest request,@RequestBody Payment payment) throws MessagingException{
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                  if(payment!=null){
            Boolean byPayment = paymentServicr.findByPayment(payment);
            if(byPayment){
                String s = mailService.sendPaymentMediaMail(email);
                if(!s.isBlank()){
                  String substring = s.substring(10);
                  if(confirmationService.addConfirmation(email, substring)){
                      return new ResponseEntity<>("Success", HttpStatus.OK);
                  
            }} }else{
                return new ResponseEntity<>("Not Found",HttpStatus.FOUND);
              }  }}
        } return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
}
