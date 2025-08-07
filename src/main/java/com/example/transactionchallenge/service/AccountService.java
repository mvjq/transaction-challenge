package com.example.transactionchallenge.service;

import com.example.transactionchallenge.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public interface AccountService {
    Account createAccount(Account account);
    Account getAccount(Long id);
}
