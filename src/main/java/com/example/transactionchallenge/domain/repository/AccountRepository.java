package com.example.transactionchallenge.domain.repository;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByDocumentNumber(AccountRequest accountRequest);
}
