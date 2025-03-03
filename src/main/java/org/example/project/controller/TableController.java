package org.example.project.controller;

import java.util.List;

import org.example.project.model.TableDTO;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.TablesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/"})
public class TableController {
    @Autowired
    private final TablesService tabservice;
   @Autowired
    private  final JwtService jwtService;
    @Autowired
    private  final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<List<TableDTO>> getTable(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            UserDetail user = userService.findByEmail(email);
            if(user != null) {
                List<TableDTO> tables = tabservice.findTables();
                return new ResponseEntity<>(tables,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
}
}
