package com.example.transactionchallenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionResponse(
        @JsonProperty("account_id") Long accountId,
        @JsonProperty("operation_type_id") int operationTypeId,
        Double amount) {}
