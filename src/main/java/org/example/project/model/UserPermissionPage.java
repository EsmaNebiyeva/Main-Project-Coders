package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserPermissionPage {
    private List<UserPermissionDto> userPermissions;
    private Integer countUserPermissions;
}

