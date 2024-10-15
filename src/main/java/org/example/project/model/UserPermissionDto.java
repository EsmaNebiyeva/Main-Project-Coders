package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.UserPermission;
import org.example.project.security.user.Role;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPermissionDto {

    private String username;
    private String email;
    private LocalDateTime created;
    @Enumerated
    private Role role;

//    public static UserPermissionDto toDto(User user) {
//        UserPermissionDto userPermissionDto = new UserPermissionDto();
//        userPermissionDto.setId(user.getId());
//        userPermissionDto.setUsername(user.getUsername());
//        userPermissionDto.setEmail(user.getEmail());
//        userPermissionDto.setRole(user.getAccessType());
//        userPermissionDto.setCreated(user.getSignUpDate());
//        return userPermissionDto;
//    }
    public static UserPermission toEntity(UserPermissionDto userPermissionDto) {
        UserPermission user = new UserPermission();
        user.setUsername(userPermissionDto.getUsername());
        user.setEmail(userPermissionDto.getEmail());
        user.setCreated(userPermissionDto.getCreated());
        user.setRole(userPermissionDto.getRole());
        return user;
    }
}
