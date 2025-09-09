package com.example.API.service;

import com.example.API.model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    Account updateAccount(Long id, Account updatedAccount);
    boolean deleteAccount(Long id);
}
