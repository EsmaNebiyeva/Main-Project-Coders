package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.example.project.security.user.Role;
import org.example.project.security.user.UserDetail;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class UserPermissionDto {

    private String username;
    private String email;
    private LocalDateTime created;
    @Enumerated
    private Role role;
    public UserPermissionDto(String email, LocalDateTime created, Role role, String username) {
        this.email = email;
        this.created = created;
        this.role = role;
        this.username = username;
    }

   public static UserPermissionDto toDto(UserDetail user) {
       UserPermissionDto userPermissionDto = new UserPermissionDto();
    
       userPermissionDto.setUsername(user.getUsername());
       userPermissionDto.setEmail(user.getEmail());
       userPermissionDto.setRole(user.getRole());
       userPermissionDto.setCreated(user.getCreated());
       return userPermissionDto;
   }
    //public static UserDetail toEntity(UserPermissionDto userPermissionDto) {
        // UserDetail user = new UserPermission();
        // user.setUsername(userPermissionDto.getUsername());
        // user.setEmail(userPermissionDto.getEmail());
        // user.setCreated(userPermissionDto.getCreated());
        // user.setRole(userPermissionDto.getRole());
        // return user;
  //  }
}
