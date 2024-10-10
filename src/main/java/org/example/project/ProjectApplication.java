package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.example.project.controller.*;

import org.example.project.repository.general.AddressRepository;
import org.example.project.repository.general.BusinessDetailsRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;

import org.example.project.repository.other.UsersRepository;
//import org.example.project.security.auth.AuthenticationController;
//import org.example.project.security.config.ApplicationConfig;
//import org.example.project.security.demo.AdminController;
//import org.example.project.security.demo.DemoController;
//import org.example.project.security.demo.ManagementController;
//import org.example.project.security.user.UserController;
import org.example.project.security.auth.AuthenticationController;
import org.example.project.security.config.ApplicationConfig;

import org.example.project.security.user.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class ProjectApplication {
private final AddressRepository addressRepository;
private final BusinessDetailsRepository businessDetailsRepository;
private final OrderRepository orderRepository;
private final ProductRepository productRepository;
private final UsersRepository usersRepository;
private final EntityManager entityManager;
private final EntityManagerFactory entityManagerFactory;
private final ProductController productController;
private final OrderController orderController;
private final BusinessDetailsController businessDetailsController;
private final UsersController usersController;
private final ApplicationConfig applicationConfig;
private final UserController userController;
private final AuthenticationController authenticationController;


    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
