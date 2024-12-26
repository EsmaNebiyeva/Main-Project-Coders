package org.example.project.controller;

import org.example.project.entity.other.Setting;
import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;
import org.example.project.security.auth.MailService;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.general.SettingService;
import org.example.project.service.notifications.MoreActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.vercel.app/"})
public class SettingController {
   @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final SettingService service;
        @Autowired
    private final MoreActivityService moreActivityService;
    @Autowired
    private final MailService mailService;

    //alindi
    @SuppressWarnings("unused")
    @PutMapping("/save")
    public ResponseEntity<Setting> save(HttpServletRequest request, @RequestBody  Setting setting) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            MoreActivityDTO activity = moreActivityService.getActivity(email);
            if (user != null) {
                setting.setUser(user);
                Setting setting2=service.getByEmailSetting(email, setting);
                if(setting==null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                    mailService.sendSetting(email);
                    }
                return new ResponseEntity<>(setting2, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //alindi
    @PutMapping("/cancel")
    public ResponseEntity<Setting> cancel(HttpServletRequest request, @RequestBody Setting setting) {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                // Create order and associate with user
                UserDetail user = userService.findByEmail(email);
                if (user != null) {
                    setting.setUser(user);
                    Setting cancel = service.cancelBymail(email, setting);
                    return new ResponseEntity<>(cancel, HttpStatus.ACCEPTED);
                }
            }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

    } 
}
