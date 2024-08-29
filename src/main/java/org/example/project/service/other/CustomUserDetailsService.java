package org.example.project.service.other;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

    @Service
    public class CustomUserDetailsService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Fetch user from the database or any other source
            // Here, for simplicity, we're returning a dummy user
            if ("user".equals(username)) {
                return org.springframework.security.core.userdetails.User
                        .withUsername("user")
                        .password("{noop}password") // {noop} means no password encoding
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        }
    }


