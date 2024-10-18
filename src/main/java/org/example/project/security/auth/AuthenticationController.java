package org.example.project.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


import org.example.project.security.config.JwtService;
import org.example.project.security.token.Token;
import org.example.project.security.user.*;
import org.example.project.service.other.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  @Autowired
  private final AuthenticationService service;
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final UserService userService;
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {

    return ResponseEntity.ok(service.register(request));
  }




  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
      return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/changePassword")
  public ResponseEntity<AuthenticationResponse> passwordChange(
          @RequestBody ChangePasswordRequests request
  )
  {
    AuthenticationResponse authenticationResponse = service.changePassword(request);
    if (authenticationResponse != null) {
      return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    } else{
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

//@PostMapping("/logout")
//  public ResponseEntity<String> logout(
//HttpServletRequest request
//){
//  String authorizationHeader = request.getHeader("Authorization");
//  if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//    String token = authorizationHeader.substring(7);
//    String email = jwtService.extractEmail(token);
//    if (email != null) {
//      Boolean b = userService.logOut(email, token);
//      if(b){
//        return new ResponseEntity<>("Logout is succesful",HttpStatus.OK);
//      }
//    }
//  }
//  return new ResponseEntity<>("Logout is not succesful",HttpStatus.BAD_REQUEST);
//}
}
