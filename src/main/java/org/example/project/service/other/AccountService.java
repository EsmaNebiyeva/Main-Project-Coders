package org.example.project.service.other;

import org.example.project.entity.other.Account;
import org.example.project.model.AccountDto;



public interface AccountService {
    AccountDto saveAccount(String email,Account account);
    boolean cancelAccount(Account account);
    boolean deleteAccount(String email,Account account);
    AccountDto getAccount(String email);
    boolean deleteAccountByEmail(String email);
 //   Boolean cleanupDeletedAccounts(String email);
}
