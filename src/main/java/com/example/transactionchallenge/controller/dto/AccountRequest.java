package com.example.transactionchallenge.controller.dto;

import jakarta.validation.constraints.NotNull;

public record AccountRequest(@NotNull String documentNumber) {}
