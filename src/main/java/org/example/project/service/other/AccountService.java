package org.example.project.service.other;

import org.example.project.entity.other.Account;

import java.util.Optional;

public interface AccountService {
    Account saveAccount(Account account);
    Optional<Account> cancelAccount(Account account);
    boolean deleteAccount(Account account);
}
