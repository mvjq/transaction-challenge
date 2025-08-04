package com.example.transactionchallenge.controller.dto;

import com.example.transactionchallenge.domain.OperationType;

public record TransactionRequest(Long accountId, OperationType operationTypeId,
                                 Double amount) {}
