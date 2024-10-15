package org.example.project.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.model.AddressDTO;
import org.example.project.model.BusinessDetailsDTO;
import org.example.project.model.InformationRequest;
import org.example.project.model.InformationRequestDTO;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.general.AddressService;
import org.example.project.service.general.BusinessDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/general")
@RequiredArgsConstructor
//ALindiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
public class BusinessDetailsController {
    @Autowired
    private final BusinessDetailsService businessDetailsService;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    //alindi
    @PutMapping("/save")
    public ResponseEntity<InformationRequestDTO> save(HttpServletRequest request, @RequestBody InformationRequest informationRequest) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                informationRequest.getBusinessDetails().setUser(user);
                informationRequest.getAddress().setUserDetails(user);
                BusinessDetailsDTO businessDetailsDTO = businessDetailsService.saveBusinessDetails(email,informationRequest.getBusinessDetails());
                AddressDTO addressDTO = addressService.saveAddress(email,informationRequest.getAddress());
                InformationRequestDTO responseDTO = new InformationRequestDTO(businessDetailsDTO, addressDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //alindi
    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(HttpServletRequest request, @RequestBody InformationRequest informationRequest) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                // Create order and associate with user
                UserDetail user = userService.findByEmail(email);
                if (user != null) {
                    businessDetailsService.cancelBusinessDetails(informationRequest.getBusinessDetails());
                    addressService.cancelAddress(informationRequest.getAddress());
                    return new ResponseEntity<>("Cancel oldu", HttpStatus.ACCEPTED);
                }
            }
        }catch(Exception e){

                    System.out.println("Northins");
                    return new ResponseEntity<>("Cancel olmadi", HttpStatus.BAD_REQUEST);
                }
        return new ResponseEntity<>("Cancel olmadi", HttpStatus.OK);

    }
}
