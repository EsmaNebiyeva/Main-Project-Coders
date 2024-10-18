package org.example.project.security.user;

import lombok.RequiredArgsConstructor;
import org.example.project.security.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServ {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository repository;

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

}
