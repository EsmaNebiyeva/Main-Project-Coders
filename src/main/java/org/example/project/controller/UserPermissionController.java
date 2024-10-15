package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.UserPermission;
import org.example.project.model.UserPermissionDto;
import org.example.project.model.UserPermissionPage;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user_permission")
//alindiiiiiii
public class UserPermissionController {
    @Autowired
  private final UserPermissionService userPermissionService;
@Autowired
private final JwtService jwtService;
    @GetMapping("/getAllFirst")
    public ResponseEntity<List<UserPermissionDto>> getUserPermissionsFirst(HttpServletRequest request,@RequestParam int page ,@RequestParam int size) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            List<UserPermission> allUsers = userPermissionService.getAllUsersFirst(email,page,size).stream().toList();
            List<UserPermissionDto> userPermissionDtos = new ArrayList<>();
            for (UserPermission userPermission : allUsers) {
                UserPermissionDto userPermission1 = new UserPermissionDto();
                userPermission1.setEmail(userPermission.getEmail());
                userPermission1.setCreated(userPermission.getCreated());
                userPermission1.setRole(userPermission.getRole());
                userPermission1.setUsername(userPermission.getUsername());
                userPermissionDtos.add(userPermission1);
            }
            return new ResponseEntity<>(userPermissionDtos, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<UserPermissionPage> getUserPermissions(HttpServletRequest request, @RequestParam int page , @RequestParam int size) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            List<UserPermission> allUsers = userPermissionService.getAllUsers(email,page,size).stream().toList();
            List<UserPermissionDto> userPermissionDtos = new ArrayList<>();
            for (UserPermission userPermission : allUsers) {
                UserPermissionDto userPermission1 = new UserPermissionDto();
                userPermission1.setEmail(userPermission.getEmail());
                userPermission1.setCreated(userPermission.getCreated());
                userPermission1.setRole(userPermission.getRole());
                userPermission1.setUsername(userPermission.getUsername());
                userPermissionDtos.add(userPermission1);
            }
            Integer i = userPermissionService.countUserPermission(email);
            UserPermissionPage userPermissionPage = new UserPermissionPage();
            userPermissionPage.setUserPermissions(userPermissionDtos);
            userPermissionPage.setCountUserPermissions(i);
            return new ResponseEntity<>(userPermissionPage, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PutMapping("/save")
    public ResponseEntity<UserPermissionPage> updateUserPermission(HttpServletRequest request, @RequestBody List<UserPermissionDto> userPermissionDto,@RequestParam int page,@RequestParam int size) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            List<UserPermission> list = userPermissionService.updateUserPermission(userPermissionDto, email, page, size).stream().toList();
            List<UserPermissionDto> userPermissionDtos = new ArrayList<>();
            for (UserPermission userPermission : list) {
                UserPermissionDto userPermission1 = new UserPermissionDto();
                userPermission1.setEmail(userPermission.getEmail());
                userPermission1.setCreated(userPermission.getCreated());
                userPermission1.setRole(userPermission.getRole());
                userPermission1.setUsername(userPermission.getUsername());
                userPermissionDtos.add(userPermission1);
            }
            Integer i = userPermissionService.countUserPermission(email);
            UserPermissionPage userPermissionPage = new UserPermissionPage();
            userPermissionPage.setUserPermissions(userPermissionDtos);
            userPermissionPage.setCountUserPermissions(i);
            return new ResponseEntity<>(userPermissionPage,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserPermission(HttpServletRequest request,@RequestBody UserPermissionDto userPermissionDto) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            userPermissionService.deleteUserPermission(email, userPermissionDto);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/cancel")
    public ResponseEntity<UserPermissionPage> cancelUserPermission(HttpServletRequest request, @RequestBody List<UserPermissionDto> userPermissionDto,@RequestParam int page,@RequestParam int size) {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                List<UserPermission> allUsers = userPermissionService.getAllUsers(email,page,size).stream().toList();
                List<UserPermissionDto> userPermissionDtos = new ArrayList<>();
                for (UserPermission userPermission : allUsers) {
                    UserPermissionDto userPermission1 = new UserPermissionDto();
                    userPermission1.setEmail(userPermission.getEmail());
                    userPermission1.setCreated(userPermission.getCreated());
                    userPermission1.setRole(userPermission.getRole());
                    userPermission1.setUsername(userPermission.getUsername());
                    userPermissionDtos.add(userPermission1);
                }
                Integer i = userPermissionService.countUserPermission(email);
                UserPermissionPage userPermissionPage = new UserPermissionPage();
                userPermissionPage.setUserPermissions(userPermissionDtos);
                userPermissionPage.setCountUserPermissions(i);
                return new ResponseEntity<>(userPermissionPage, HttpStatus.FOUND);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    }

