package org.example.project.security.user;

import lombok.RequiredArgsConstructor;
import org.example.project.model.UserDTO;
import org.example.project.repository.general.SettingRepository;
import org.example.project.repository.other.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.example.project.model.UserDTO.convertToUserDto;

@Service
@RequiredArgsConstructor
public class UserService implements UserServ {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final SettingRepository settingRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (UserDetail) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    @Override
    public UserDetail findByEmail(String email) {
        if(email != null) {
            return repository.getByEmail(email);
        }
        return null;
    }

    @Override
    public List<UserDetail> getAll() {
        List<UserDetail> all = repository.findAll();
        return all;
    }

    @Override
    public UserDTO getByEmail(String email) {
        if(email != null) {
            UserDetail byEmail = repository.getByEmail(email);
            UserDTO userDTO = convertToUserDto(byEmail,settingRepository,accountRepository,settingRepository);
            return userDTO;
        }
        return null;
    }
 @Override
 public Boolean getUserByEmail(String email){
    if(email != null) {
        UserDetail byEmail = repository.getByEmail(email);
       if(byEmail!=null){
        return true;
       }
        return false;
    }
    return null;
 }
//    @Override
//    public Boolean logOut(String email,String token) {
//        Optional<UserDetail> byEmail = repository.findByEmail(email);
//        if(byEmail.isPresent()) {
//            List<Token> tokens = byEmail.get().getTokens();
//            for(Token t : tokens) {
//                if(t.getToken().equals(token)) {
//                    tokens.remove(t);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
@Override
@Transactional
public Boolean deleteByEmail(String email){
    repository.deleteByEmail(email);
    return true;
}
public void processOAuthPostLogin(String email) {
    Optional<UserDetail> existUser = repository.findByEmail(email);
     if(!existUser.isPresent()){
    if (existUser == null) {
        UserDetail newUser = new UserDetail();
        newUser.setEmail(email);
        repository.save(newUser);        
    }
}
}
}
