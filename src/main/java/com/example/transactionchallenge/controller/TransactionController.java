package com.example.transactionchallenge.controller;

import com.example.transactionchallenge.TransactionFacade;
import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionFacade  transactionFacade;

    public TransactionController(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    @GetMapping("/accounts")
    public AccountResponse getAccount(@RequestParam Long id) {
        return new AccountResponse();
    }

    @PostMapping("/accounts")
    public AccountResponse createAccount(@RequestBody AccountRequest accountRequest) {
        return new AccountResponse();
    }

    @PostMapping("/transaction")
    public TransactionResponse createTransaction(@RequestBody AccountRequest accountRequest) {
        return new TransactionResponse();
    }

}
