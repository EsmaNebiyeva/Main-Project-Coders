package org.example.project.security.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserServ {
    UserDetail findByEmail(String email);
   List< UserDetail> getAll();
}
