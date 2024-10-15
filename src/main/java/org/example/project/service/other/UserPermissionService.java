package org.example.project.service.other;


import org.example.project.entity.other.UserPermission;
import org.example.project.model.UserPermissionDto;
import org.example.project.security.user.UserDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserPermissionService {
    Page<UserPermission> getAllUsersFirst(String email,Integer page, Integer size );
    Page<UserPermission> getAllUsers(String email,Integer page, Integer size );
     Page<UserPermission> updateUserPermission(List<UserPermissionDto> permission, String email,Integer page,Integer size);
     boolean deleteUserPermission(String email,UserPermissionDto userPermission);
    boolean addUserPermission(UserDetail permission);
    Integer countUserPermission(String email);
}
