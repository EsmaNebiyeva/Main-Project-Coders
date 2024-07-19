package org.example.project.service;

import org.example.project.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
     void save(User user);
//     void update(User user);
     void delete(User user);
     Optional<User> findByUsername(String username);
     List<User> findAll();
     User addUser(User user);
}
