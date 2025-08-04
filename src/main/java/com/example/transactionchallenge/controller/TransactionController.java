package com.example.transactionchallenge.controller;

import com.example.transactionchallenge.TransactionFacade;
import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionFacade  transactionFacade;

    public TransactionController(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    @GetMapping("/accounts")
    public AccountResponse getAccount(@RequestParam Long id) {
        return transactionFacade.getAccount(id);
    }

    @PostMapping("/accounts")
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        transactionFacade.createAccount(accountRequest);
    }

    @PostMapping("/transaction")
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionFacade.createTransaction(transactionRequest);
    }

}
