package org.example.project.service.other.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.other.UserPermission;
import org.example.project.exception.OurException;
import org.example.project.model.UserPermissionDto;
import org.example.project.repository.other.UserPermissionRepository;

import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.example.project.service.other.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.example.project.model.UserPermissionDto.toEntity;
import static org.thymeleaf.util.StringUtils.concat;

//import static org.example.project.model.UserPermissionDto.toEntity;

@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl implements UserPermissionService {
    @Autowired
    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;

    @Override
    public Page<UserPermission> getAllUsersFirst(String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<UserDetail> all = userRepository.findAll();
        UserDetail byEmail = userRepository.getByEmail(email);
        List<UserPermission> userPermissions = new ArrayList<>();
        for (UserDetail userDetail : all) {
            UserPermission userPermission1 = new UserPermission();
            userPermission1.setUser(byEmail);
            userPermission1.setEmail(userDetail.getEmail());
            userPermission1.setCreated(userDetail.getCreated());
            userPermission1.setRole(userDetail.getRole());
            userPermission1.setUsername(concat(userDetail.getFirstname(),userDetail.getFirstname()));
            userPermissions.add(userPermission1);
        }
        userPermissionRepository.saveAll(userPermissions);
        Page<UserPermission> byEmail1 = userPermissionRepository.findByEmail(email, pageable);
        if (!byEmail1.isEmpty()) {
            return byEmail1;
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<UserPermission> getAllUsers(String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserPermission> byEmail1 = userPermissionRepository.findByEmail(email, pageable);
        if (!byEmail1.isEmpty()) {
            return byEmail1;
        } else {
            return Page.empty();
        }
    }


    @Override
    public Page<UserPermission> updateUserPermission(List<UserPermissionDto> permission, String email, Integer page, Integer size) {
        // E-poçtu çıxarırıq
        List<UserPermission> byEmail = userPermissionRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            UserDetail byEmail1 = userRepository.getByEmail(email);
            List<UserPermission> userPermissions = new ArrayList<>();
            for (UserPermissionDto userPermission : permission) {
                UserPermission userPermission1 = userPermissionRepository.findByEmailList(email, userPermission.getEmail());
                UserPermission entity = toEntity(userPermission);
                userPermission1.setUser(byEmail1);
                userPermission1.setEmail(entity.getEmail());
                userPermission1.setCreated(entity.getCreated());
                userPermission1.setRole(entity.getRole());
                userPermission1.setUsername(entity.getUsername());
            }
           // List<UserPermission> userPermissions2 = userPermissionRepository.saveAll(userPermissions);
            Pageable pageable = PageRequest.of(page, size);
            Page<UserPermission> byEmail2 = userPermissionRepository.findByEmail(email, pageable);
            return byEmail2;
        }
        return Page.empty();
    }

    @Transactional
    @Override
    public boolean deleteUserPermission(String email, UserPermissionDto userPermission) {
        List<UserPermission> byEmail = userPermissionRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            UserPermission userPermission1 = new UserPermission();
            userPermission1.setEmail(userPermission.getEmail());
            userPermission1.setCreated(userPermission.getCreated());
            userPermission1.setRole(userPermission.getRole());
            userPermission1.setUsername(userPermission.getUsername());
            String email1 = userPermission.getEmail();
            userPermissionRepository.deleteByEmailAndUser(email, email1);
            return true;
        }
        return false;
    }


    @Transactional
    @Override
    public boolean addUserPermission(UserDetail userPermission) {
            if (userPermission != null) {
                UserPermission userPermission1 = new UserPermission();
                userPermission1.setEmail(userPermission.getEmail());
                userPermission1.setCreated(userPermission.getCreated());
                userPermission1.setRole(userPermission.getRole());
                userPermission1.setUsername(userPermission.getUsername());
                userPermissionRepository.save(userPermission1);
                return true;
            }
            else{
                return false;
            }
    }

    @Override
    public Integer countUserPermission(String email) {
        Integer i = userPermissionRepository.countByEmail(email);
        if (i>0) {
            return i;
        } else{
            return 0;
        }
    }
}


   // @Override
//    public UserPermission saveUser(UserDetail userDetail) {
//        UserPermission save = userPermissionRepository.save(userDetail);
//        return save;
//    }

