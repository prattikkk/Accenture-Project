package com.example.API.controller;

import com.example.API.dto.FundTransferRequest;
import com.example.API.dto.TransactionDTO;
import com.example.API.model.Transaction;
import com.example.API.response.ApiResponse;
import com.example.API.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDTO>> create(@Valid @RequestBody Transaction transaction) {
        TransactionDTO createdTransaction = transactionService.createTransaction(
                transaction.getAccount().getId(), transaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("✅ Transaction processed successfully", true, createdTransaction));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getAll() {
        List<TransactionDTO> transactions = transactionService.getAllTransactionDTOs();
        return ResponseEntity.ok(new ApiResponse<>("📄 All transactions fetched", true, transactions));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionDTO>> transfer(@RequestBody FundTransferRequest request) {
        TransactionDTO result = transactionService.transferFunds(request);
        return ResponseEntity.ok(new ApiResponse<>("💸 Fund transferred successfully", true, result));
    }

}
