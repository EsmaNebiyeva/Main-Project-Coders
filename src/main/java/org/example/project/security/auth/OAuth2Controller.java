package org.example.project.security.auth;

import lombok.RequiredArgsConstructor;

import org.example.project.service.notifications.OuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.vercel.app/"})
public class OAuth2Controller {
    @Autowired

    // @GetMapping("/outh/code/google")
    // public String handleGoogleOAuth2Callback(@RequestParam("code") String code,
    //                                           @RequestParam("state") String state) {
    //     // PKCE için code_verifier parametrelerini doğruladıktan sonra
    //     String codeVerifier = "code_verifier_from_frontend";  // Frontend'den alınan code_verifier'ı burada kullanın

    //     // Backend'e authorization code ve code_verifier ile access_token al
    //     String accessToken = oauth2Service.getAccessToken(code, codeVerifier);

    //     // Access token'ı kullanarak kullanıcının bilgilerini alabilirsiniz
    //     return "Access Token: " + accessToken;
    // }

    @GetMapping("/error")
    public String handleError() {
        // Provide a custom error page view
        return "index"; // error.html in resources/templates (if using Thymeleaf) or static error page
    }
    @GetMapping("/oauth2/code/google")
    public String handleOAuth2Redirect(@RequestParam String code, @RequestParam String state) {
        // Handle the code received from Google
        // Exchange the code for an access token and process the user information
        return "Login Successful";
    }
    @GetMapping("/home")
    public Object geString(Model model,@AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("userName", user.getAttribute("name"));
        model.addAttribute("password", user.getAttribute("password"));
        model.addAttribute("email", user.getAttribute("email"));
        // Provide a custom error page view
        return model.toString(); // error.html in resources/templates (if using Thymeleaf) or static error page
    }
    // @GetMapping()
    // public String geStrin() {
    //     // Provide a custom error page view
    //     return "error"; // error.html in resources/templates (if using Thymeleaf) or static error page
    // }
}
