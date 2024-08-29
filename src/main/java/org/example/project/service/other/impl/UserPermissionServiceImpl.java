package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.User;
import org.example.project.entity.other.UserPermission;
import org.example.project.exception.OurException;
import org.example.project.model.UserPermissionDto;
import org.example.project.repository.other.UserPermissionRepository;
import org.example.project.repository.other.UserRepository;
import org.example.project.service.other.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import static org.example.project.model.UserPermissionDto.toEntity;

@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl implements UserPermissionService {
    @Autowired
    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
    @Override
    public List<UserPermissionDto> getAllUsers() {
        try {
            UserPermissionDto userPermission = new UserPermissionDto();
            List<User> all = userRepository.findAll();
            List<UserPermissionDto> userPermissionDto = new ArrayList<>();
//            for (User user : all) {
//                userPermission.setId(user.getId());
//                userPermission.setUsername(user.getUsername());
//                userPermission.setRole(user.getAccessType());
//                userPermission.setEmail(user.getEmail());
//                userPermission.setCreated(user.getSignUpDate());
//                userPermissionDto.add(userPermission);
//            }
            return userPermissionDto;
        }
        catch (Exception e) {
            throw new OurException("Wrong var");
        }
    }
@Transactional
    @Override
    public UserPermission addUserPermission(UserPermission permission) {
        try{
            if(permission!=null) {
                userPermissionRepository.save(permission);
                return permission;
            }
        } catch (Exception e) {System.out.println("Wrong var");
            throw new OurException("Wrong var");

        }
       return null;
    }
    @Transactional
    @Override
    public UserPermission deleteUserPermission(UserPermissionDto userPermission) {
////     try{  if(userPermission!=null) {
////           userPermissionRepository.delete(toEntity(userPermission));
////       }
//    }catch (Exception e) {
//     throw new OurException("Wrong var");
//     }
     return null;
    }
}
