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
    public AccountDto saveAccount(AccountDto account3) {
        Account account = fromDTOToNormal(account3);
        Optional<Account> byId = accountRepository.findByName(account.getName());
        if(byId.isPresent()) {
         Account account1= byId.get();
         account1.setName(account.getName());
         account1.setEmail(account.getEmail());
         account1.setPassword(account.getPassword());
         account1.setBirthDate(account.getBirthDate());
         account1.setGender(account.getGender());
       accountRepository.save(account1);
      return convertToDto(account1);
    }  else {
     accountRepository.save(account);
     return convertToDto(account);
}
    }


    @Override
    public Optional<AccountDto> cancelAccount (AccountDto account){
         this.accountRepository.findByName(account.getName());
         return Optional.of(account);
    }

    @Transactional
    @Override
    public boolean deleteAccount(AccountDto account1) {
        try {
            Account account = fromDTOToNormal(account1);
            if(accountRepository.existsById(account.getId())) {
               // Thread.sleep(1209600000 );
                accountRepository.delete(account);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Wrong var");
            throw new OurException("Data is not found");
        }
        return false;
    }
}