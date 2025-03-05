package org.example.project.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


import org.example.project.model.UserDTO;

import org.example.project.security.config.JwtService;
import org.example.project.security.token.Token;
import org.example.project.security.token.TokenRepository;
import org.example.project.security.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import java.io.IOException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/","https://posive.vercel.app/"})
public class AuthenticationController {

  @Autowired
  private final AuthenticationService service;
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final UserService userService;
@Autowired
private final TokenRepository repository; 
@Autowired
private final GoogleTokenService googleTokenService;

@Autowired
private final RestTemplate restTemplate;
// @Autowired
// private final AccountRepository aRepository;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    AuthenticationResponse register = service.register(request);
    if(register!=null){
    return new ResponseEntity<>(register,HttpStatus.OK);
    }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
@GetMapping("/login")
public String getLoginPage() {
    return "login";
}

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request ) { 
        // Boolean userByEmail = userService.getUserByEmail(request.getEmail());
        // if(userByEmail){
        //   Optional<Account> byEmail = aRepository.findByEmail(request.getEmail());
        //   if(byEmail.isPresent()){
        //     //if(byEmail.get().getDeletedDate().plusDays(14).isAfter(LocalDate.now())){
           AuthenticationResponse authenticate = service.authenticate(request);
        //   //if (authenticate!=null) {
      return new ResponseEntity <> (authenticate,HttpStatus.OK);
   
   
    }
  @PostMapping("/google")
    public ResponseEntity<AuthenticationResponse> verifyGoogleToken(@RequestParam String idToken) throws Exception {
        String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
           String response = restTemplate.getForObject(url, String.class);
           Payload verifyToken = googleTokenService.verifyToken(idToken);
              String email = verifyToken.getEmail();
              AuthenticationResponse googleRegister = service.googleRegister(response);
            return ResponseEntity.ok(googleRegister); // İstifadəçi məlumatlarını qaytarır
       
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

  @PostMapping("/refresh")
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

   @GetMapping("/getUser")
   public ResponseEntity<UserDTO> getUser(HttpServletRequest request){
     String authorizationHeader = request.getHeader("Authorization");

     if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
       String token = authorizationHeader.substring(7);
       Optional<Token> byToken = repository.findByToken(token);
       if(byToken.isPresent()){
        if(byToken.get().expired!=true && byToken.get().revoked!=true){
       String email = jwtService.extractEmail(token);
       UserDTO user = userService.getByEmail(email);
       if(user!=null) {
         return new ResponseEntity<>(user, HttpStatus.OK);
       }else{
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
     }
    }else{
       return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
   } return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
