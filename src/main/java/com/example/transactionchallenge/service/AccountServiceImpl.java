package com.example.transactionchallenge.service;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.domain.DomainConverter;
import com.example.transactionchallenge.domain.entity.Account;
import com.example.transactionchallenge.domain.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.findByDocumentNumber(account.getDocumentNumber())
                .orElse(accountRepository.save(account));
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
