package org.example.project.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Product;
import org.example.project.entity.User;
import org.example.project.repository.*;
import org.example.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/project-java")
//@RequiredArgsConstructor
public class ExampleController {
//    @Autowired
//    private final AddressService addressService;
//    @Autowired
//    private final BusinessDetailsService businessDetailsService;
//    @Autowired
//    private final OrderService orderService;
//    @Autowired
//    private final ProductService productService;
//    @Autowired
//    private final UserService userService;
//
//
//    @PostMapping("/createProduct")
//    public ResponseEntity<String> createProduct(@RequestBody Product product) {
//        productService.addProduct(product);
//        return new ResponseEntity<>("Data elave olundu", HttpStatus.CREATED);
//    }
//
//    @GetMapping("/getProduct")
//    public ResponseEntity<List<Product>> getAllProducts(@RequestParam int page,
//                                                        @RequestParam int size) {
//        List<Product> products = productService.getProducts(page, size).getContent();
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
//    @GetMapping("/getallProduct")
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> products = productService.getProducts();
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
}
