package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.example.project.controller.ExampleController;
import org.example.project.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class ProjectApplication {
private final AddressRepository addressRepository;
private final BusinessDetailsRepository businessDetailsRepository;
private final OrderRepository orderRepository;
private final ProductRepository productRepository;
private final UserRepository userRepository;
private final EntityManager entityManager;
private final EntityManagerFactory entityManagerFactory;
private final ExampleController exampleController;
    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
