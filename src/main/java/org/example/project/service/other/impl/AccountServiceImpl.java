package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Account;
import org.example.project.exception.OurException;
import org.example.project.model.AccountDto;
import org.example.project.repository.other.AccountRepository;
import org.example.project.service.other.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.project.model.AccountDto.convertToDto;
import static org.example.project.model.AccountDto.fromDTOToNormal;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public AccountDto saveAccount(String email, Account account) {
        Optional<Account> byId = accountRepository.findByEmail(email);
        if (byId.isPresent()) {
            Account account1 = byId.get();
            account1.setName(account.getName());
            account1.setEmail(account.getEmail());
            account1.setPassword(account.getPassword());
            account1.setBirthDate(account.getBirthDate());
            account1.setGender(account.getGender());

           return convertToDto(account1);
        }else {
            accountRepository.save(account);
            return convertToDto(account);
        }
    }



    @Override
    public boolean cancelAccount (Account account){
         return true;
    }

    @Transactional
    @Override
    public boolean deleteAccount(String email,Account account) {

            Optional<Account> byEmail = accountRepository.findByEmail(email);
            if(byEmail.isPresent()) {
                Account byEmailAndUser = accountRepository.findByEmailAndUser(email, byEmail.get().getEmail());
                accountRepository.delete(byEmailAndUser);
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