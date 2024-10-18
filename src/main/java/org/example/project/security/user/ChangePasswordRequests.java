package org.example.project.security.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequests {
    private String email;
    private String newPassword;
}
