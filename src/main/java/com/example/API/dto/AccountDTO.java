package com.example.API.dto;

import com.example.API.model.AccountType;

import java.math.BigDecimal;

public class AccountDTO {
    private Long id;
    private String accountHolderName;
    private AccountType accountType;
    private BigDecimal balance;

    // Getters
    public Long getId() {
        return id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // Manual Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AccountDTO accountDTO;

        public Builder() {
            accountDTO = new AccountDTO();
        }

        public Builder id(Long id) {
            accountDTO.setId(id);
            return this;
        }

        public Builder accountHolderName(String name) {
            accountDTO.setAccountHolderName(name);
            return this;
        }

        public Builder accountType(AccountType type) {
            accountDTO.setAccountType(type);
            return this;
        }

        public Builder balance(BigDecimal balance) {
            accountDTO.setBalance(balance);
            return this;
        }

        public AccountDTO build() {
            return accountDTO;
        }
    }
}
