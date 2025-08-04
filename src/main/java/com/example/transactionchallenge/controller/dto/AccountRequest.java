package com.example.transactionchallenge.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(@NotBlank String documentNumber) {}
