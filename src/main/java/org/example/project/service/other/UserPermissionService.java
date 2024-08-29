package org.example.project.service.other;


import org.example.project.entity.other.UserPermission;
import org.example.project.model.UserPermissionDto;

import java.util.List;

public interface UserPermissionService {
    List< UserPermissionDto> getAllUsers();
     UserPermission addUserPermission(UserPermission permission);
     UserPermission deleteUserPermission(UserPermissionDto userPermission);
}
