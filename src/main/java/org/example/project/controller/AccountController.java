package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Account;
import org.example.project.service.other.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor()
public class AccountController {
    @Autowired
    private final AccountService accountService;

    @PutMapping("/save")
    public ResponseEntity<Account> save(@RequestBody Account account) {
        try {
            accountService.saveAccount(account);

            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Xeta");
            return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody Account account) {
        try {
            accountService.cancelAccount(account);
            return new ResponseEntity<>("Cancel oldu", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("Nothing");
            return new ResponseEntity<>("Cancel olmadi", HttpStatus.BAD_REQUEST);
        }
    }
@DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Account account) {
    try {
        if (accountService.deleteAccount(account)) {
            return new ResponseEntity<>("Delete oldu", HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>("Delete olmadi", HttpStatus.BAD_REQUEST);
        }
    } catch (Exception e) {
        System.out.println("Delete olmadi");
        return new ResponseEntity<>("Delete olunmadi", HttpStatus.BAD_REQUEST);
    }
}
}
