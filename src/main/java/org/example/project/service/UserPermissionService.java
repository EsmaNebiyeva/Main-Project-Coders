package org.example.project.service;


import org.example.project.entity.UserPermission;
import org.example.project.model.UserPermissionDto;

import java.util.List;

public interface UserPermissionService {
    List< UserPermissionDto> getAllUsers();
     UserPermission addUserPermission(UserPermission permission);
     UserPermission deleteUserPermission(UserPermissionDto userPermission);
}
