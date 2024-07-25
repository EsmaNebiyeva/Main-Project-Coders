package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
import org.example.project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private final AddressService addressService;
    @GetMapping("/get")
    public ResponseEntity<Address> getAddress(@RequestBody Address address) {
      try{
          addressService.saveAddress(address);
          return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
      }
      catch(Exception e){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

    }
    @DeleteMapping("delete")
    public ResponseEntity<Address> deleteAddress(@RequestBody Address address) {
        try {
            addressService.deleteAddress(address);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
    }
}
