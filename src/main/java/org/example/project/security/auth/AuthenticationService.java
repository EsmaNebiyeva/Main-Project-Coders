package org.example.project.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.general.Address;
import org.example.project.entity.general.BusinessDetails;
import org.example.project.entity.notification.EmailNot;
import org.example.project.entity.notification.MoreActivity;
import org.example.project.entity.other.Account;
//import org.example.project.model.MoreActivitiesEnum;
import org.example.project.repository.general.AddressRepository;
import org.example.project.repository.general.BusinessDetailsRepository;
import org.example.project.repository.notifications.EmailNotRepository;
import org.example.project.repository.notifications.MoreActivityRepository;
import org.example.project.repository.other.AccountRepository;
import org.example.project.security.config.JwtService;
import org.example.project.security.token.Token;
import org.example.project.security.token.TokenRepository;
import org.example.project.security.token.TokenType;
import org.example.project.security.user.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.example.project.service.notifications.EmailNotService;
// import org.example.project.service.notifications.MoreActivityService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
private final BusinessDetailsRepository businessDetailsRepository;
private final AddressRepository addressRepository;
  private final AccountRepository accountRepository;
  private final EmailNotRepository emailNotRepo;
  private final MoreActivityRepository moreActivityRepo;
  @Autowired
private final MailService mailService;

  public AuthenticationResponse register(RegisterRequest request) {
    String[] result = request.getFullName().split(" ", 2);
    if(repository.getByEmail(request.getEmail()) == null) {
      var user = UserDetail.builder()
              .firstname(result[0])
              .lastname(result[1])
              .email(request.getEmail())
              .phoneNumber(request.getPhoneNumber())
              .password(passwordEncoder.encode(request.getPassword()))
              .role(Role.MEMBER)
              .source("log in")
              .created(LocalDateTime.now())
              .build();
      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);
      saveUserToken(savedUser, jwtToken);
      Account account=new Account(request.getFullName(),request.getPassword(),request.getPhoneNumber(),request.getEmail());
      Address address=new Address(user);
      addressRepository.save(address);
      BusinessDetails businessDetails=new BusinessDetails(user);
      businessDetailsRepository.save(businessDetails);
      accountRepository.save(account);
      EmailNot emailNot=new EmailNot();
      MoreActivity moreActivity=new MoreActivity();
      emailNot.setEmail(request.getEmail());
      moreActivity.setEmail(request.getEmail());
      moreActivityRepo.save(moreActivity);
      emailNotRepo.save(emailNot);
      mailService.sendMail(request.getEmail());
      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();
    }else{
      return null;
    }
  }

  public AuthenticationResponse changePassword(ChangePasswordRequests request) {
    String email = request.getEmail();
    Optional<UserDetail> byEmail = repository.findByEmail(email);
    accountRepository.findByEmail(email);
    if(byEmail.isPresent()) {
      byEmail.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
      accountRepository.findByEmail(email).get().setPassword(request.getNewPassword());
      var jwtToken = jwtService.generateToken(byEmail.get());
      var refreshToken = jwtService.generateRefreshToken(byEmail.get());
      revokeAllUserTokens(byEmail.get());
      saveUserToken(byEmail.get(), jwtToken);
      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();
    }else{
      return null;
    }
  }

  public AuthenticationResponse googleRegister(String request) {
    try {
      // JSON'u Map'e dönüştür
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> payload = objectMapper.readValue(request, Map.class);

      // Gerekli alanları al
      String name = (String) payload.get("name");
      String email = (String) payload.get("email");
      long exp = Long.parseLong(payload.get("exp").toString());
      long iat = Long.parseLong(payload.get("iat").toString());
      long expiration=exp-iat;
      java.util.Date expirationDate = new java.util.Date(expiration * 1000L);
      String[] result = name.split(" ", 2);
      if(repository.getByEmail(email) == null) {
        var user = UserDetail.builder()
                .firstname(result[0])
                .lastname(result[1])
                .email(email)
                .role(Role.MEMBER)
                .source("google")
                .created(LocalDateTime.now())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        Account account=new Account(name,"*******","",email);
        Address address=new Address(user);
        addressRepository.save(address);
        BusinessDetails businessDetails=new BusinessDetails(user);
        businessDetailsRepository.save(businessDetails);
        accountRepository.save(account);
        EmailNot emailNot=new EmailNot();
        MoreActivity moreActivity=new MoreActivity();
        emailNot.setEmail(email);
        moreActivity.setEmail(email);
        moreActivityRepo.save(moreActivity);
        emailNotRepo.save(emailNot);
        mailService.sendRegister(email);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
      } else{
        UserDetail byEmail = repository.getByEmail(email);
        Optional<Account> byEmail1 = accountRepository.findByEmail(email);
        if(byEmail1.isPresent()){
          byEmail1.get().setDeletedDate(null);
        }
       var jwtToken = jwtService.generateToken(byEmail);
        var refreshToken = jwtService.generateRefreshToken(byEmail);
        revokeAllUserTokens(byEmail);
        saveUserToken(byEmail, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
      }
    
  } catch (Exception e) {
      e.printStackTrace();
  
    }
    return null;
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
        Optional<Account> byEmail = accountRepository.findByEmail(request.getEmail());
        if(byEmail.isPresent()){
          byEmail.get().setDeletedDate(null);
        }
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(UserDetail user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserDetail user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
