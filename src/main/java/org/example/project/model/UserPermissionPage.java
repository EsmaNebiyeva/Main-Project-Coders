package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;
import org.example.project.entity.other.UserPermission;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserPermissionPage {
    private List<UserPermissionDto> userPermissions;
    private int countUserPermissions;
}

