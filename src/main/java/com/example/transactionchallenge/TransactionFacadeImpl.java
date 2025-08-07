package com.example.transactionchallenge;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import com.example.transactionchallenge.domain.DomainConverter;
import com.example.transactionchallenge.domain.entity.Transaction;
import com.example.transactionchallenge.domain.repository.AccountRepository;
import com.example.transactionchallenge.domain.repository.TransactionRepository;
import com.example.transactionchallenge.service.AccountServiceImpl;
import com.example.transactionchallenge.service.TransactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TransactionFacadeImpl implements TransactionFacade {

    private final AccountServiceImpl accountService;
    private final TransactionServiceImpl transactionService;
    private final DomainConverter converter;

    public TransactionFacadeImpl(AccountServiceImpl accountService, TransactionServiceImpl transactionService, DomainConverter converter) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.converter = converter;
    }

    @Override
    public AccountResponse getAccount(Long id) {
        return converter.toResponse(accountService.getAccount(id));
    }

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {

        if (accountRequest.documentNumber() == null ||
            accountRequest.documentNumber().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Document number is required and cannot be empty");
        }

        var account = converter.toEntity(accountRequest);
        var saved = accountService.createAccount(account);
        return converter.toResponse(saved);
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        if (transactionRequest.accountId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account ID is required and cannot be empty");
        }

        Transaction transaction =
                transactionService.createTransaction(transactionRequest);
        return converter.toResponse(transaction);
    }
}
