package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.UserPermission;
import org.example.project.model.UserPermissionDto;
import org.example.project.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user_permission")
public class UserPermissionController {
    @Autowired
    private final UserPermissionService userPermissionService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserPermissionDto>> getUserPermissions() {
       try{
           List<UserPermissionDto> allUsers = userPermissionService.getAllUsers();
           return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
       } catch (Exception e){
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
    @PostMapping("/add")
    public ResponseEntity<UserPermission> addUserPermission(@RequestBody UserPermission userPermissionDto) {
        try{
            UserPermission userPermission = userPermissionService.addUserPermission(userPermissionDto);
            return new ResponseEntity<>(userPermission,HttpStatus.CREATED);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<UserPermission> deleteUserPermission(@RequestBody UserPermissionDto userPermissionDto) {
        try{
            UserPermission userPermission = userPermissionService.deleteUserPermission(userPermissionDto);
            return new ResponseEntity<>(userPermission,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
