package com.example.transactionchallenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountResponse(
        @JsonProperty("account_id") Long accountId,
        @JsonProperty("document_number") String documentNumber) {}
