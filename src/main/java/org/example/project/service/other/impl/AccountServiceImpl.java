package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Account;

import org.example.project.model.AccountDto;

import org.example.project.repository.other.AccountRepository;

import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;

import org.example.project.service.other.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static org.example.project.model.AccountDto.convertToDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final UserRepository uRepository2repository;
    @Autowired
   private final PasswordEncoder passwordEncoder ;
    // @Autowired
    // private final AccountService accountService;
    // @Autowired
    // private final AccountRepository arepository;
    // @Autowired
    // private final UserService userService;
    // @Autowired
    // private final JwtService jwtService;
    // @Autowired
    // private final TokenRepository repository;
    // @Autowired
    // private final ConfirmationRepository cRepository;
    // @Autowired
    // private final ProductRepository productService;
    // @Autowired
    // private final OrderRepository orderRepository;
    // @Autowired
    // private final UserPermissionRepository uRepository;
    // @Autowired
    // private final SubscriptionRepository sRepository;
    // @Autowired 
    // private final SettingRepository sRepository2;

    @Transactional
    @Override
    public AccountDto saveAccount(String email, Account account) {
        Optional<Account> byId = accountRepository.findByEmail(email); 
        
        if (byId.isPresent()) {
            if(account.getPassword().isBlank()){
            account.setPassword(byId.get().getPassword());
        }
            Account account1 = byId.get();
            Optional<UserDetail> byEmail = uRepository2repository.findByEmail(email);
            if(byEmail.isPresent()){
                String[] split2 = account.getName().split(" ",2);
                byEmail.get().setFirstname(split2[0]);
                byEmail.get().setPassword(passwordEncoder.encode(account.getPassword()));
                byEmail.get().setLastname(split2[1]);
                byEmail.get().setImageUrl(account.getImage());
                byEmail.get().setPhoneNumber(account.getPhone());
            }
            account1.setName(account.getName());
            account1.setEmail(email);
            account1.setPassword(account.getPassword());
            account1.setBirthDate(account.getBirthDate());
            account1.setGender(account.getGender());
            account1.setImage(account.getImage());
            account1.setPhone(account.getPhone());
            account1.setDeletedDate(null);
        
           return convertToDto(account1);
        }else {
            return null;
        }
    
    }


// @Scheduled(cron = "0 0 0 * * ?") // This runs every day at midnight
//     public Boolean cleanupDeletedAccounts(String email) {
//         LocalDate cutoffDate = LocalDate.now().plusDays(14);
//        Optional<Account> expiredAccounts = accountRepository.findByEmail(email);
// if(expiredAccounts.isPresent()){
//     if(expiredAccounts.get().getDeletedDate().plusDays(14).isEqual(LocalDate.now())){
//         Optional<Account> byEmail = accountRepository.findByEmail(email);
//         Optional<UserDetail> byEmail2 = uRepository2repository.findByEmail(email);
//         if(byEmail2.isPresent()){
//         if(byEmail.isPresent()) {
//             Account account = byEmail.get();
//             accountRepository.delete(account);
//             repository.deleteByUserId(byEmail2.get().getId());
//             cRepository.deleteByEmail(email);
//             productService.deleteByEmail(email);
//             orderRepository.deleteByEmail(email);
//             sRepository2.deleteByEmail(email);
//             uRepository.deleteByEmail(email);
//             sRepository.deleteByEmail(email);

//              Boolean deleteByEmail = userService.deleteByEmail( email);
//             return true;
//     }
// }
//     }
// } return false;
//     }

    @Override
    public boolean cancelAccount (Account account){
         return true;
    }

    @Transactional
    @Override
    public boolean deleteAccount(String email,Account account) {

            Optional<Account> byEmail = accountRepository.findByEmail(email);
            if(byEmail.isPresent()) {
                Account byEmailAndUser = accountRepository.findByEmail(email).get();
                accountRepository.delete(byEmailAndUser);
                return true;
            }else {
                return false;
            }
    }
    @Transactional
    @Override
    public boolean deleteAccountByEmail(String email) {

            Optional<Account> byEmail = accountRepository.findByEmail(email);
            if(byEmail.isPresent()) {
                Account account = byEmail.get();
                accountRepository.delete(account);
                return true;
            }else {
                return false;
            }
    }
    @Override
    public AccountDto getAccount(String email) {
       Account byEmail = accountRepository.findByEmail(email).get();
       if(byEmail != null) {
           return convertToDto(byEmail);
       }
        return null;
    }
}