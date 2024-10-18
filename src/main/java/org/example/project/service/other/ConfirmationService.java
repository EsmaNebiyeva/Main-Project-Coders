package org.example.project.service.other;

public interface ConfirmationService {
    Boolean findByEmailAndPassword(String email,String password);
    Boolean addConfirmation(String email,String password);
}
