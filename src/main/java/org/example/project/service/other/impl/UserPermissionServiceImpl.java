package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.other.Account;
import org.example.project.entity.other.UserPermission;

import org.example.project.model.UserPermissionDto;
import org.example.project.repository.other.AccountRepository;
import org.example.project.repository.other.ConfirmationRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.repository.other.UserPermissionRepository;
import org.example.project.repository.subscribetion.SubscriptionRepository;
import org.example.project.security.token.TokenRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.example.project.service.other.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




//import static org.example.project.model.UserPermissionDto.toEntity;

@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl implements UserPermissionService {
    @Autowired
    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
     private final TokenRepository repository;
    @Autowired
    private final ConfirmationRepository cRepository;
    @Autowired
    private final ProductRepository productService;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserPermissionRepository uRepository;
    @Autowired
    private final SubscriptionRepository sRepository;
    @Autowired
    private final AccountRepository accountRepository;


    // @Override
    // public Page<UserPermission> getAllUsersFirst(String email, Integer page,
    // Integer size) {
    // Pageable pageable = PageRequest.of(page, size);
    // List<UserDetail> all = userRepository.findAll();
    // UserDetail byEmail = userRepository.getByEmail(email);
    // List<UserPermission> userPermissions = new ArrayList<>();
    // for (UserDetail userDetail : all) {
    // UserPermission userPermission1 = new UserPermission();
    // userPermission1.setUser(byEmail);
    // userPermission1.setEmail(userDetail.getEmail());
    // userPermission1.setCreated(userDetail.getCreated());
    // userPermission1.setRole(userDetail.getRole());
    // userPermission1.setUsername(concat(userDetail.getFirstname(),
    // userDetail.getFirstname()));
    // userPermissions.add(userPermission1);
    // }
    // userPermissionRepository.saveAll(userPermissions);
    // Page<UserPermission> byEmail1 = userPermissionRepository.findByEmail(email,
    // pageable);
    // if (!byEmail1.isEmpty()) {
    // return byEmail1;
    // } else {
    // return Page.empty();
    // }
    // }

    @Override
    public Page<UserDetail> getAllUsers(String email, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDetail> byEmail1 = userRepository.findByEmails(email, pageable);
        if (!byEmail1.isEmpty()) {
            return byEmail1;
        } else {
            return Page.empty();
        }
    }

    @Override
    public UserPermissionDto updateUserPermission(UserPermissionDto permission) {
        Optional<UserDetail> byEmail = userRepository.getByEmailsDetail(permission.getEmail());
        if(byEmail.isPresent()){
            UserDetail userDetail = byEmail.get();
            String[] split = permission.getUsername().split(" ",2);
            
            if (split.length > 0) {
                userDetail.setFirstname(split[0]); // İlk kelimeyi firstname olarak al
            }
            
            if (split.length > 1) {
                userDetail.setLastname(split[1]); // İkinci kelimeyi lastname olarak al
            } else {
                userDetail.setLastname(" "); // Eğer ikinci kelime yoksa, lastname'e boş string ata
            }
            Account account = accountRepository.findByEmail(permission.getEmail()).get();
            if(account!=null){
                account.setName(userDetail.getFirstname()+" "+userDetail.getLastname());
            }
            else{
                return null;
            }
            userDetail.setCreated(permission.getCreated());
            userDetail.setRole(permission.getRole());
            userRepository.save(userDetail);
            accountRepository.save(account);
            return permission;
        }
        return null;
        }
    

    @Transactional
    @Override
    public boolean deleteUserPermission( String userPermission) {
        Optional<UserDetail> byEmail = userRepository.getByEmailsDetail(userPermission);
        if(byEmail.isPresent()){
            userRepository.deleteByEmail(userPermission);
            productService.deleteByEmail(userPermission);
            orderRepository.deleteByEmail(userPermission);
            cRepository.deleteByEmail(userPermission);
            uRepository.deleteByEmail(userPermission);
            sRepository.deleteByEmail(userPermission);
            repository.deleteById(byEmail.get().getId());
            return true;
        }
        return false;
    }


    @Transactional
    @Override
    public boolean deleteAllUserPermission( List<String> userPermission) {
        boolean okey=true;
        for(String userPermissionDto:userPermission){
        Optional<UserDetail> byEmail = userRepository.getByEmailsDetail(userPermissionDto);
        if (byEmail.isPresent()) {
            userRepository.deleteByEmail(userPermissionDto);
            productService.deleteByEmail(userPermissionDto);
            orderRepository.deleteByEmail(userPermissionDto);
            cRepository.deleteByEmail(userPermissionDto);
            uRepository.deleteByEmail(userPermissionDto);
            sRepository.deleteByEmail(userPermissionDto);
            repository.deleteById(byEmail.get().getId());
        }else{
            okey=false;
        }
    }
    if(okey){
        return true;
    } return false;
    }
    @Transactional
    @Override
    public boolean addUserPermission(UserDetail userPermission) {
        if (userPermission != null) {
            UserPermission userPermission1 = new UserPermission();
            userPermission1.setEmail(userPermission.getEmail());
            userPermission1.setCreated(userPermission.getCreated());
            userPermission1.setRole(userPermission.getRole());
            userPermission1.setUsername(userPermission.getUsername());
            userPermissionRepository.save(userPermission1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer countUserPermission(String email) {
        Integer i = userRepository.findByEmailList(email);
        if (i > 0) {
            return i;
        } else {
            return 0;
        }
    }
}

// @Override
// public UserPermission saveUser(UserDetail userDetail) {
// UserPermission save = userPermissionRepository.save(userDetail);
// return save;
// }
