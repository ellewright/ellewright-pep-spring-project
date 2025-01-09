package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private MessageService messageService;

    public AccountService(AccountRepository accountRepository, MessageService messageService) {
        this.accountRepository = accountRepository;
        this.messageService = messageService;
    }

    public Account register(Account newAccount) {
        List<Account> allAccounts = (List<Account>) accountRepository.findAll();

        for (Account account : allAccounts) {
            if (newAccount.getUsername().equals(account.getUsername())) {
                return null;
            }
        }

        if (!newAccount.getUsername().equals("") && newAccount.getPassword().length() > 3) {
            return accountRepository.save(newAccount);
        }

        return null;
    }

    public Account login(Account accountToLogIn) {
        List<Account> allAccounts = (List<Account>) accountRepository.findAll();

        for (Account account : allAccounts) {
            if (accountToLogIn.getUsername().equals(account.getUsername()) && accountToLogIn.getPassword().equals(account.getPassword())) {
                Optional<Account> optionalAccount = accountRepository.findById(account.getAccountId());

                if (optionalAccount.isPresent()) {
                    Account loggedInAccount = optionalAccount.get();
                    return loggedInAccount;
                }
            }
        }

        return null;
    }
}
