package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.User;
import org.example.project.repository.other.UserRepository;
import org.example.project.service.other.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
@Autowired
    private final UserRepository userRepository;
    @Override
    public void update(User user) {
        userRepository.save(user);
        System.out.println("Elave olundu save");
    }
//
//    @Override
//    public void update(User user) {
//        if(!userRepository.existsById(user.getId())) {
//            userRepository.save(user);
//            System.out.println("Elave olundu amma yenisi uypdate");
//        }else{
//            this.userRepository.save(user);
//            System.out.println("Elave olundu uypdate");
//        }
//    }

    @Override
    public void delete(User user) {
        if(userRepository.existsById(user.getId())) {
            userRepository.deleteById(user.getId());
            System.out.println("Data silindi");
        }else{
            System.out.println("Data silinmedi");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
      return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
