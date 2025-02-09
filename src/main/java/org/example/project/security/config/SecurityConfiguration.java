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

}
