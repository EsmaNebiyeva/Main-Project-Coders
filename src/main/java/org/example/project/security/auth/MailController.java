package org.example.project.security.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/","https://posive.vercel.app/"})
public class MailController {
    @Autowired
    private final MailService mailService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
@Autowired
private final ConfirmationService confirmationService;

//    @GetMapping("/normal")
//    public ResponseEntity<String> sendNormalMail(@RequestParam String email) throws MessagingException {
//
//            UserDetail user = userService.findByEmail(email);
//            if (user != null) {
//                return ResponseEntity.ok(mailService.sendMail(email));
//            }
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> sendMultiMail(@RequestParam String email) throws MessagingException {
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
        String s = mailService.sendMultiMediaMail(email);
       if(!s.isBlank()){
           String substring = s.substring(10);
           if(confirmationService.addConfirmation(email, substring)){
               return new ResponseEntity<>(substring, HttpStatus.OK);
           }
       }
            }

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verifyPayment")
    public ResponseEntity<String> sendPaymentMail(HttpServletRequest request) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                // Create order and associate with user
                UserDetail user = userService.findByEmail(email);
            if (user != null) {
         String s = mailService.sendPaymentMediaMail(email);
         if(!s.isBlank()){
           String substring = s.substring(10);
           if(confirmationService.addConfirmation(email, substring)){
               return new ResponseEntity<>(substring, HttpStatus.OK);
           }
       }
            }

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("verifyPasswordPayment")
    public ResponseEntity<String> confirmPaymentMail(HttpServletRequest request,@RequestParam String confirmPassword) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                // Create order and associate with user
                UserDetail user = userService.findByEmail(email);
            if (user != null) {
        if(email!=null &&confirmPassword!=null){
            if(userService.findByEmail(email)!=null){
                if(confirmationService.findByEmailAndPassword(email, confirmPassword)){
                    return new ResponseEntity<>("Accept",HttpStatus.OK);
                }
            }
        }
    }
}
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    
}
    @GetMapping("verifyPassword")
    public ResponseEntity<String> confirmPasswordMail(@RequestParam String email,@RequestParam String confirmPassword) throws MessagingException {
        if(email!=null &&confirmPassword!=null){
            if(userService.findByEmail(email)!=null){
                if(confirmationService.findByEmailAndPassword(email, confirmPassword)){
                    return new ResponseEntity<>("Accept",HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}