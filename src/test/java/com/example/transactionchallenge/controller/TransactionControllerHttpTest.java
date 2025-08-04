package com.example.transactionchallenge.controller;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import com.example.transactionchallenge.domain.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerHttpTest {

    @Autowired
    private WebTestClient webTestClient;

    // Family 2XX
    @Test // FIXME: 200 -> 201
    void shouldCreateTransactionAndReturn200() {
        var request = new TransactionRequest(1L,
                OperationType.PURCHASE_INSTALLMENTS, 10.00);

        webTestClient.post()
                .uri("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), TransactionRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionResponse.class);
    }

    @Test // FIXME: 200 -> 201
    void shouldCreateAccountAndReturn200() {
        var request = new AccountRequest( "documentNumber");
        webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(request), AccountRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class);

    }

    @Test // FIXME: 200 -> 201
    void shouldGetAccountAndReturn200(@Autowired WebTestClient webTestClient) {
        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/accounts")
                                .queryParam("id", "1")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class);
    }

    // Family 4XX
}
