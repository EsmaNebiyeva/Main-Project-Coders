// package org.example.project.controller;

// import com.google.common.net.HttpHeaders;
// import com.google.oauth.dto.IdTokenRequestDto;
// import com.google.oauth.service.AccountService;

// import org.example.project.security.config.JwtService;
// import org.example.project.security.user.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseCookie;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import javax.servlet.http.HttpServletResponse;

// @RestController
// @RequestMapping("/v1/oauth")
// public class LoginControllers {
//   @Autowired
//   private final JwtService jwtService;
//   @Autowired
//   private final UserService userService;

//     @PostMapping("/login")
//     public ResponseEntity LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto requestBody, HttpServletResponse response) {
//         String authToken = userService.loginOAuthGoogle(requestBody);
//         final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", authToken)
//                 .httpOnly(true)
//                 .maxAge(7 * 24 * 3600)
//                 .path("/")
//                 .secure(false)
//                 .build();
//         response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//         return ResponseEntity.ok().build();
//     }
// }