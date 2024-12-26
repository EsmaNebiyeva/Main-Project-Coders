package org.example.project.service.other;



import org.example.project.model.UserPermissionDto;
import org.example.project.security.user.UserDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserPermissionService {
   // Page<UserPermission> getAllUsersFirst(String email,Integer page, Integer size );
    Page<UserDetail> getAllUsers(String email,Integer page, Integer size );
     UserPermissionDto updateUserPermission(UserPermissionDto permission);
     boolean deleteUserPermission(String userPermission);
    boolean deleteAllUserPermission(List<String> userPermissionDtos);
    boolean addUserPermission(UserDetail permission);
    Integer countUserPermission(String email);
}
