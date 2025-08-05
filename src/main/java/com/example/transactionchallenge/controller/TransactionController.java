package com.example.transactionchallenge.controller;

import com.example.transactionchallenge.TransactionFacade;
import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionFacade  transactionFacade;

    public TransactionController(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    @GetMapping("/accounts/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return transactionFacade.getAccount(id);
    }

    @PostMapping("/accounts")
    public AccountResponse createAccount(@RequestBody AccountRequest accountRequest) {
        return transactionFacade.createAccount(accountRequest);
    }

    @PostMapping("/transaction")
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionFacade.createTransaction(transactionRequest);
    }

}
