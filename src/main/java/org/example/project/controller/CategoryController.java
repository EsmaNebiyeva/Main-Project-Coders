package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project.model.CategoryDTO;
import org.example.project.security.config.JwtService;
import org.example.project.service.other.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/category")
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/"})
@RequiredArgsConstructor()
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final JwtService jwtService;

    @GetMapping("/getByName")
    public ResponseEntity<CategoryDTO> getAll(HttpServletRequest request, @RequestParam String name) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            CategoryDTO category = categoryService.getCategory(email,name);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
