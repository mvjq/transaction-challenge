package com.example.transactionchallenge;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.domain.DomainConverter;
import com.example.transactionchallenge.domain.repository.AccountRepository;
import com.example.transactionchallenge.domain.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TransactionFacadeImpl implements TransactionFacade {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final DomainConverter converter;

    public TransactionFacadeImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, DomainConverter converter) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.converter = converter;
    }

    @Override
    public AccountResponse getAccount(Long id) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return converter.fromAccount(account);
    }

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        var account = converter.fromRequest(accountRequest);
        var saved =
                accountRepository.findByDocumentNumber(accountRequest.documentNumber().toString())
                .orElse(accountRepository.save(account));
        return converter.fromAccount(saved);
    }

    @Override
    public void createTransaction(TransactionRequest transactionRequest) {
        var account =
                accountRepository.findById(transactionRequest.accountId())
                        .orElseThrow(() ->
                                new  ResponseStatusException(HttpStatus.NOT_FOUND));
        var transaction = converter.fromRequest(transactionRequest, account);

        transactionRepository.save(transaction);
    }
}
