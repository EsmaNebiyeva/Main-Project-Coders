package org.example.project.security.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
public enum Role {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    private String role;

    // Constructor
    Role(String role) {
        this.role = role;
    }

    // Getter method
    public String getRole() {
        return role;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + name()));
    }
}
