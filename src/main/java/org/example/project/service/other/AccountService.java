package org.example.project.service.other;

import org.example.project.entity.other.Account;
import org.example.project.model.AccountDto;

import java.util.Optional;

public interface AccountService {
    AccountDto saveAccount(AccountDto account);
    Optional<AccountDto> cancelAccount(AccountDto account);
    boolean deleteAccount(AccountDto account);
}
