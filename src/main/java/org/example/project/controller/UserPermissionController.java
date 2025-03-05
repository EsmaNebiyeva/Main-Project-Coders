package org.example.project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;
import org.example.project.model.UserPermissionDto;
import org.example.project.model.UserPermissionPage;
import org.example.project.security.auth.MailService;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.Role;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.notifications.MoreActivityService;
import org.example.project.service.other.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user_permission")
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/","https://posive.vercel.app/"})
// alindiiiiiii
public class UserPermissionController {
    @Autowired
    private final UserPermissionService userPermissionService;
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final MoreActivityService moreActivityService;
    @Autowired
    private final MailService mailService;

    @GetMapping("/getAll")
    public ResponseEntity<UserPermissionPage> getUserPermissions(HttpServletRequest request, @RequestParam int page,
            @RequestParam int size) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            UserDetail user = userService.findByEmail(email);
           
            if (user!=null&& (user.getRole().toString().equalsIgnoreCase(Role.SUPER_ADMIN.toString())
                    || user.getRole().toString().equalsIgnoreCase(Role.ADMIN.toString()))) {
                List<UserDetail> allUsers = userPermissionService.getAllUsers(email, page, size).stream().toList();
                List<UserPermissionDto> userPermissionDtos = new ArrayList<>();
                for (UserDetail userPermission : allUsers) {
                    UserPermissionDto userPermission1 = new UserPermissionDto();
                    userPermission1.setEmail(userPermission.getEmail());
                    userPermission1.setCreated(userPermission.getCreated());
                    userPermission1.setRole(userPermission.getRole());
                    userPermission1.setUsername(userPermission.getFirstname().concat(" ").concat(userPermission.getLastname()));
                    userPermissionDtos.add(userPermission1);
                }
                Integer i = userPermissionService.countUserPermission(email);
                UserPermissionPage userPermissionPage = new UserPermissionPage();
                userPermissionPage.setUserPermissions(userPermissionDtos);
                userPermissionPage.setCountUserPermissions(i);
                return new ResponseEntity<>(userPermissionPage, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserPermissionDto> updateUserPermission(HttpServletRequest request,
            @RequestBody UserPermissionDto userPermissionDto) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            MoreActivityDTO activity = moreActivityService.getActivity(email);
            UserPermissionDto userPermissionDtos = userPermissionService.updateUserPermission(userPermissionDto);

            if (userPermissionDtos != null) {
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendUserPer(email,"UPDATE OLDU");
                    }
                return new ResponseEntity<>(userPermissionDtos, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserPermission(HttpServletRequest request,
            @RequestParam String email) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email1 = jwtService.extractEmail(token);
            MoreActivityDTO activity = moreActivityService.getActivity(email1);
            boolean deleteUserPermission = userPermissionService.deleteUserPermission(email);
            if (deleteUserPermission) {
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendUserPer(email1,"DELETE OLDU");
                    }
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
            return new ResponseEntity<>("OLMADI", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllUserPermission(HttpServletRequest request,
            @RequestBody List<String> email) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email1 = jwtService.extractEmail(token);
            MoreActivityDTO activity = moreActivityService.getActivity(email1);
            boolean deleteUserPermission = userPermissionService.deleteAllUserPermission(email);
            if (deleteUserPermission) {
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendUserPer(email1,"DELETE ALL OLDU");
                    }
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
            return new ResponseEntity<>("OLMADI", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
    }

}
