package com.example.transactionchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@WebMvcTest
public class TransactionControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
}
