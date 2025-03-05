package org.example.project.controller;


import java.util.List;

import org.example.project.entity.notification.EmailNot;
import org.example.project.entity.notification.MoreActivity;
import org.example.project.model.EmailNotDTO;

import org.example.project.model.MoreActivityDTO;
import org.example.project.model.Notifications;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.notifications.EmailNotService;
import org.example.project.service.notifications.MoreActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/notifications")
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/","https://posive.vercel.app/"})
@RequiredArgsConstructor()
public class NotificationsController {
    @Autowired
    private final EmailNotService emailNotService;
   @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
@Autowired
private final MoreActivityService moreActivityService;
  @PostMapping("/save")
    public ResponseEntity<String> save(HttpServletRequest request, @RequestParam List<String> nList,@RequestParam List<String> mList) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                EmailNot activity = emailNotService.addActivity(nList, email);
                MoreActivity activity2 = moreActivityService.addActivity(mList, email);
                if(activity!=null && activity2!=null){
                return new ResponseEntity<>("OKEY",HttpStatus.OK);
                }
                return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/get")
    public ResponseEntity<Notifications> get(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                EmailNotDTO activity = emailNotService.getActivity( email);
                MoreActivityDTO activity2 = moreActivityService.getActivity(email);
                Notifications notifications=new Notifications();
                if(activity!=null && activity2!=null){
                notifications.setEmailNotDTO(activity);
                notifications.setMoreActivityDTO(activity2);
                return new ResponseEntity<>(notifications,HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
