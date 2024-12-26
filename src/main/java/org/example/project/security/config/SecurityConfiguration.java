package org.example.project.security.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;

import static org.example.project.security.user.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity

public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/api/mail/**", "/api/setting/**",
            "/login/**" };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Autowired
    private OAuthAuthenicationSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                // .formLogin(login->
                // login.loginPage("/api/v1/auth/signin") // Giriş sayfasının URL'si
                // .defaultSuccessUrl("/api/product/getALL", true) // Giriş başarılı olduktan
                // sonra yönlendirme
                // //.failureUrl("/login?error=true")
                // .permitAll())
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers("/api/account/**", "/api/general/**", "/api/order/**", "/api/payment/**","/api/user_permission/**",
                                "/api/subscriptions/**", "/api/tables/**", "/api/product/get")
                        .hasAnyRole(MEMBER.getRole(), ADMIN.getRole(), SUPER_ADMIN.getRole())
                        .requestMatchers("/api/product/**", "/api/notifications/**", "/api/subscriptions/**",
                                "/api/category/getByName", "/api/user_permission/**")
                        .hasAnyRole(ADMIN.getRole(), SUPER_ADMIN.getRole())

                        .anyRequest()
                        .authenticated()

                )
                .oauth2Login(oauth2 -> {
                    oauth2.successHandler(oAuth2LoginSuccessHandler);
                    oauth2.defaultSuccessUrl("/login/home", true);

                    // oauth2.failureUrl("/login?error=true");
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext())
                        .logoutSuccessUrl("/api/v1/auth/register?logout"));

        return http.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // @Bean
    // public ClientRegistration googleClientRegistration() {
    // return ClientRegistration.withRegistrationId("google")
    // .clientId("213079391893-t8e22e0h36id49foosilhrihbc6o07u7.apps.googleusercontent.com")
    // .clientSecret("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")
    // .scope("email", "profile","openid") // Gerekli olan izinler
    // .redirectUri("http://localhost:8080/login/oauth2/code/google") // Google
    // callback URL
    // .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth") // Google
    // authorization URL
    // .tokenUri("https://oauth2.googleapis.com/token") // Token URL
    // .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo") // User info
    // URL
    // .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Grant
    // type ekleyin
    // .build();
    // }
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http.cors().and()
    // .csrf().disable()
    // .authorizeHttpRequests(req ->
    // req.requestMatchers(WHITE_LIST_URL)
    // .permitAll())

    // .oauth2Login()
    // .loginPage("/login")
    // .defaultSuccessUrl("/home", true)
    // .failureUrl("/login?error=true")
    // .successHandler(oAuth2LoginSuccessHandler) // Burada OAuth2 oturum açma
    // başarılı olduğunda işlemler yapılır
    // .and()
    // .sessionManagement()
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    // .and()
    // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
    // .authenticationProvider(authenticationProvider)
    // .logout()
    // .logoutUrl("/logout")
    // .logoutSuccessUrl("/login?logout=true")
    // .invalidateHttpSession(true)
    // .clearAuthentication(true);
    // return http.build();
    // }

    // @Bean
    // public ClientRegistrationRepository clientRegistrationRepository() {
    // return new InMemoryClientRegistrationRepository(clientRegistration());
    // }

    // @Bean
    // public ClientRegistration clientRegistration() {
    // return ClientRegistration.withRegistrationId("google")
    // .clientId("YOUR_GOOGLE_CLIENT_ID")
    // .clientSecret("YOUR_GOOGLE_CLIENT_SECRET")
    // .scope("email", "profile")
    // .redirectUri("{baseUrl}/login/oauth2/code/google")
    // .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
    // .tokenUri("https://oauth2.googleapis.com/token")
    // .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
    // .build();
    // }
    // @Bean
    // public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
    // return new DefaultOAuth2UserService();
    // }
    // @Bean
    // public JwtDecoder jwtDecoder() {
    // return JwtDecoders.fromIssuerLocation("https://my-auth-server.com");
    // }

    // @Value("${app.cors.allowedOrigins}")
    // private List<String> allowedOrigins;

    // @Bean
    // public CorsFilter corsFilter() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(allowedOrigins); // List olarak kullanıyoruz
    // configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE",
    // "OPTIONS"));
    // configuration.setAllowedHeaders(List.of("Authorization", "Content-Type",
    // "Accept"));
    // configuration.setAllowCredentials(true); // Cookies gibi kimlik bilgilerini
    // desteklemek için

    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration); // Tüm rotalar için
    // geçerli

    // CorsFilter corsFilter = new CorsFilter(source);
    // return corsFilter;
    // }
}
