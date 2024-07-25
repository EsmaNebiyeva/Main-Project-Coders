package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.BusinessDetails;
import org.example.project.service.BusinessDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bdc")
@RequiredArgsConstructor
public class BusinessDetailsController {
    @Autowired
    private final BusinessDetailsService businessDetailsService;
    @PostMapping("/save")
    public ResponseEntity<BusinessDetails> save(@RequestBody BusinessDetails businessDetails) {
        try{businessDetailsService.saveBusinessDetails(businessDetails);
        return ResponseEntity.ok(businessDetails);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<BusinessDetails> cancel(@RequestBody BusinessDetails businessDetails) {
        try {
            BusinessDetails businessDetails1 = businessDetailsService.cancelBusinessDetails(businessDetails);
            return ResponseEntity.ok(businessDetails1);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
