package com.example.transactionchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Family 2XX
    @Test
    void shouldCreateTransactionAndReturn200() {}

    @Test
    void shouldCreateAccountAndReturn200() {}

    @Test
    void shouldGetAccountAndReturn200() {}

    // Family 4XX
}
