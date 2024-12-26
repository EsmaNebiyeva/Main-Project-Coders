package org.example.project.controller;

import org.example.project.model.UserPermissionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserPage {
    private UserPermissionDto userPermissions;
    private int countUserPermissions;
}