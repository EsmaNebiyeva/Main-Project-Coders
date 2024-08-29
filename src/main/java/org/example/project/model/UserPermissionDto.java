package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPermissionDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private LocalDateTime created;
    private String role;

//    public static UserPermissionDto toDto(User user) {
//        UserPermissionDto userPermissionDto = new UserPermissionDto();
//        userPermissionDto.setId(user.getId());
//        userPermissionDto.setUsername(user.getUsername());
//        userPermissionDto.setEmail(user.getEmail());
//        userPermissionDto.setRole(user.getAccessType());
//        userPermissionDto.setCreated(user.getSignUpDate());
//        return userPermissionDto;
//    }
//    public static UserPermission toEntity(UserPermissionDto userPermissionDto) {
//        User user = new User();
//        user.setUsername(userPermissionDto.getUsername());
//        user.setEmail(userPermissionDto.getEmail());
//        user.setAccessType(userPermissionDto.getRole());
//        user.setSignUpDate(userPermissionDto.getCreated());
//        user.setId(userPermissionDto.getId());
//        UserPermission userPermission = new UserPermission();
//        userPermission.setId(user.getId());
//        userPermission.setUser(user);
//        return userPermission;
//    }
}
