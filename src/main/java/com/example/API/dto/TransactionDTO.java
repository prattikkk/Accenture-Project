package com.example.API.dto;

import com.example.API.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDateTime timestamp;
    private Long accountId;

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Long getAccountId() { return accountId; }

    public void setId(Long id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setType(TransactionType type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final TransactionDTO transactionDTO;
        public Builder() { transactionDTO = new TransactionDTO(); }

        public Builder id(Long id) { transactionDTO.setId(id); return this; }
        public Builder amount(BigDecimal amount) { transactionDTO.setAmount(amount); return this; }
        public Builder type(TransactionType type) { transactionDTO.setType(type); return this; }
        public Builder description(String description) { transactionDTO.setDescription(description); return this; }
        public Builder timestamp(LocalDateTime timestamp) { transactionDTO.setTimestamp(timestamp); return this; }
        public Builder accountId(Long accountId) { transactionDTO.setAccountId(accountId); return this; }

        public TransactionDTO build() { return transactionDTO; }
    }
}
