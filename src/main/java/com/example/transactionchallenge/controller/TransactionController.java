package com.example.transactionchallenge.controller;

import com.example.transactionchallenge.TransactionService;
import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/accounts/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return transactionService.getAccount(id);
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        var created = transactionService.createAccount(accountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        var created = transactionService.createTransaction(transactionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
