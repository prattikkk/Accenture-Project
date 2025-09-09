package com.example.API.service;

import com.example.API.dto.FundTransferRequest;
import com.example.API.dto.TransactionDTO;
import com.example.API.model.*;
import com.example.API.repository.*;
import exception.ApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionDTO createTransaction(Long accountId, Transaction transaction) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ApiException("Account with ID " + accountId + " not found."));

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Transaction amount must be greater than 0.");
        }

        transaction.setAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        BigDecimal currentBalance = account.getBalance();
        BigDecimal txnAmount = transaction.getAmount();

        if (transaction.getType() == TransactionType.DEBIT) {
            if (currentBalance.compareTo(txnAmount) < 0) {
                throw new ApiException("Insufficient balance for debit transaction.");
            }
            account.setBalance(currentBalance.subtract(txnAmount));
        } else if (transaction.getType() == TransactionType.CREDIT) {
            account.setBalance(currentBalance.add(txnAmount));
        } else {
            throw new ApiException("Invalid transaction type. Must be CREDIT or DEBIT.");
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToDTO(savedTransaction);
    }

    @Override
    public List<TransactionDTO> getTransactionByAccountId(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new ApiException("Account with ID " + accountId + " does not exist.");
        }

        List<Transaction> transactions = transactionRepository.findByAccount_Id(accountId);
        return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getAccount() == null || transaction.getAccount().getId() == null) {
            throw new ApiException("Transaction must include a valid Account with ID.");
        }
        TransactionDTO dto = createTransaction(transaction.getAccount().getId(), transaction);
        return transactionRepository.findById(dto.getId())
                .orElseThrow(() -> new ApiException("Transaction not saved properly."));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Transaction not found with ID: " + id));
    }

    @Override
    public List<TransactionDTO> getAllTransactionDTOs() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .timestamp(transaction.getTimestamp())
                .accountId(transaction.getAccount().getId())
                .build();
    }

    @Override
    @Transactional
    public TransactionDTO transferFunds(FundTransferRequest request) {
        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new ApiException("Cannot transfer funds to the same account.");
        }

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new ApiException("From Account not found."));
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new ApiException("To Account not found."));

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Transfer amount must be greater than 0.");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new ApiException("Insufficient balance in source account.");
        }

        // Debit from source
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));

        // Credit to destination
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Log two transactions: one debit, one credit
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setAmount(request.getAmount());
        debitTransaction.setType(TransactionType.DEBIT);
        debitTransaction.setDescription("Transfer to Account ID " + request.getToAccountId());
        debitTransaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setAmount(request.getAmount());
        creditTransaction.setType(TransactionType.CREDIT);
        creditTransaction.setDescription("Transfer from Account ID " + request.getFromAccountId());
        creditTransaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(creditTransaction);

        return mapToDTO(debitTransaction); // Return the debit part as response
    }

 
}