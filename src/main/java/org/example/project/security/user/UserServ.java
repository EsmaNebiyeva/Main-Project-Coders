package org.example.project.security.user;


import org.example.project.model.UserDTO;


import java.util.List;


public interface UserServ {
    UserDetail findByEmail(String email);
   List< UserDetail> getAll();
   UserDTO getByEmail(String email);
   Boolean getUserByEmail(String email);
  // Boolean logOut(String email,String token);
  Boolean deleteByEmail(String email);
}
