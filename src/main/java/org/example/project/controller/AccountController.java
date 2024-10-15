package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Account;
import org.example.project.model.AccountDto;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.example.project.model.AccountDto.fromDTOToNormal;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor()
//ALINDIiiiiiii
public class AccountController {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    @PutMapping("/save")
    public ResponseEntity<AccountDto> save(HttpServletRequest request, @RequestBody AccountDto account) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                Account account1 = fromDTOToNormal(account);
                account1.setUserDetail(user);
                System.out.println("AAAAAAAAAAA");
                AccountDto accountDto = accountService.saveAccount(email, account1);

                return new ResponseEntity<>(accountDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
@GetMapping("/get")
public ResponseEntity<AccountDto> get(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                AccountDto account = accountService.getAccount(email);
                return new ResponseEntity<>(account,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(HttpServletRequest request, @RequestBody Account account) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                accountService.cancelAccount(account);
                return new ResponseEntity<>("Cancel oldu", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(HttpServletRequest request, @RequestBody Account account) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                if (accountService.deleteAccount(email,account)) {
                    return new ResponseEntity<>("Delete oldu", HttpStatus.ACCEPTED);
                } else{
                    return new ResponseEntity<>("Delete olmadi",HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

