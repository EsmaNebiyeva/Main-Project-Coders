package org.example.project.service;

import org.example.project.entity.Account;
import org.example.project.entity.Address;

import java.util.Optional;

public interface AccountService {
    Account saveAccount(Account account);
    Optional<Account> cancelAccount(Account account);
    boolean deleteAccount(Account account);
}
