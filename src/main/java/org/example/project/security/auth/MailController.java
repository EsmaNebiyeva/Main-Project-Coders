package org.example.project.security.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
    @Autowired
    private final MailService mailService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    @GetMapping("/normal")
    public ResponseEntity<String> sendNormalMail(@RequestParam String email) throws MessagingException {

//            UserDetail user = userService.findByEmail(email);
//            if (user != null) {
                return ResponseEntity.ok(mailService.sendMail(email));
//            }
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/multi")
    public ResponseEntity<String> sendMultiMail(String email) throws MessagingException {

            // Create order and associate with user
//            UserDetail user = userService.findByEmail(email);
//            if (user != null) {
                return  ResponseEntity.ok(mailService.sendMultiMediaMail(email));
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}