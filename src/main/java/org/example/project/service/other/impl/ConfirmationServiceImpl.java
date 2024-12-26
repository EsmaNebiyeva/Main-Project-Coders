package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Confirmation;
import org.example.project.repository.other.ConfirmationRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;

import org.example.project.service.other.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {
    @Autowired
    private final ConfirmationRepository confirmationRepository;
    @Autowired
    private final UserRepository userRepository;
    @Override
    public Boolean findByEmailAndPassword(String email, String password) {
        if(email!=null && password!=null) {
            Confirmation byEmailAndPassword = confirmationRepository.findByEmailAndPassword(email, password);
            if(byEmailAndPassword!=null && byEmailAndPassword.getExpireDate().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean addConfirmation(String email, String password) {
        if(email!=null && password!=null) {
            Optional<UserDetail> byEmail = userRepository.findByEmail(email);
            if(byEmail.isPresent()) {
                Confirmation confirmation = new Confirmation(byEmail.get(), password);
                confirmationRepository.save(confirmation);
                return true;
            }
        }
        return false;
    }
}
