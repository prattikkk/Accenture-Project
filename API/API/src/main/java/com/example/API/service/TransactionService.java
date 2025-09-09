package com.example.API.service;

import com.example.API.dto.FundTransferRequest;
import com.example.API.dto.TransactionDTO;
import com.example.API.model.Transaction;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(Long accountId, Transaction transaction);
    List<TransactionDTO> getTransactionByAccountId(Long accountId);
    Transaction createTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(Long id);
    List<TransactionDTO> getAllTransactionDTOs();
    TransactionDTO transferFunds(FundTransferRequest request);

}