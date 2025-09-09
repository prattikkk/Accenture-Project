package com.example.API.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.API.model.Account;
import com.example.API.response.ApiResponse;
import com.example.API.service.AccountService;

import java.util.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create account
    @PostMapping
    public ResponseEntity<ApiResponse<Account>> create(@Valid @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Account created successfully", true, createdAccount));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAll() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(new ApiResponse<>("Fetched all accounts", true, accounts));
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<Account>> getById(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        Account acc = accountService.getAccountById(id);
        if (acc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Account not found with ID: " + id, false, null));
        }
        return ResponseEntity.ok(new ApiResponse<>("Account found", true, acc));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Account>> update(@Valid @RequestBody Account account) {
        if (account.getId() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Account ID must be provided.", false, null));
        }
        Account updated = accountService.updateAccount(account.getId(), account);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Cannot update. Account not found with ID: " + account.getId(), false, null));
        }
        return ResponseEntity.ok(new ApiResponse<>("Account updated successfully", true, updated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        boolean deleted = accountService.deleteAccount(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Cannot delete. Account not found with ID: " + id, false, null));
        }
        return ResponseEntity.ok(new ApiResponse<>("Account deleted successfully", true, null));
    }

}
