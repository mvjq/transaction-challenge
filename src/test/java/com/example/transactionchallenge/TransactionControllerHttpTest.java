package com.example.transactionchallenge;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import com.example.transactionchallenge.domain.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerHttpTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateAccountAndReturn200() {
        var request = new AccountRequest("39293399865");
        webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(request), AccountRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class);
    }

    @Test
    void shouldCreateAccountAndGetAccount() {
        var request = new AccountRequest("39293399865");
        AccountResponse createdAccount = webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(request), AccountRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.accountId()).isNotNull();
        assertThat(createdAccount.documentNumber()).isEqualTo("39293399865");

        webTestClient.get()
                .uri("/accounts/{accountId}", createdAccount.accountId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class)
                .consumeWith(response -> {
                    AccountResponse retrievedAccount = response.getResponseBody();
                    assertThat(retrievedAccount).isNotNull();
                    assertThat(retrievedAccount.accountId()).isEqualTo(createdAccount.accountId());
                    assertThat(retrievedAccount.documentNumber()).isEqualTo(
                            "39293399865");
                });
    }

    @Test
    void getNonExistsAccountShouldReturn404() {
        var request = new AccountRequest("39293399865");
        webTestClient.get()
                .uri("/accounts/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldCreateAccountWithNullableDocumentNumberAndReturn400() {
        var request = new AccountRequest(null);
        webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(request), AccountRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createValidAccountAndTransactionWithPositiveOperationAndReturn200() {

    }

    @Test
    void createValidAccountAndTransactionWithNegativeOperationAndReturn200() {

    }

    @Test
    void createTransactionWithoutAccountAndReturn404() {
        var request = new TransactionRequest(1L,
                OperationType.WITHDRAWAL.getValue(), 200.00);
        webTestClient.post()
                .uri("/transaction")
                .body(Mono.just(request), TransactionResponse.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createTransactionWithNegativeNumberReturn500() {
        var accountRequest = new AccountRequest("39293399865");
        webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(accountRequest), AccountRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class);
        var transactionRequest = new TransactionRequest(1L,
                OperationType.WITHDRAWAL.getValue(), -200.00);
        webTestClient.post()
                .uri("/transaction")
                .body(Mono.just(transactionRequest), TransactionResponse.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void createTransactionWithZeroAmountAndReturn500() {
        var accountRequest = new AccountRequest("39293399865");
        webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(accountRequest), AccountRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class);
        var transactionRequest = new TransactionRequest(1L,
                OperationType.WITHDRAWAL.getValue(), 0.0);
        webTestClient.post()
                .uri("/transaction")
                .body(Mono.just(transactionRequest), TransactionResponse.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void createTransactionWrongOperationTypeAndReturn500() {
        var accountRequest = new AccountRequest("39293399865");
        webTestClient.post()
                .uri("/accounts")
                .body(Mono.just(accountRequest), AccountRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class);
        var transactionRequest = new TransactionRequest(1L,
                -10, 10.0);
        webTestClient.post()
                .uri("/transaction")
                .body(Mono.just(transactionRequest), TransactionResponse.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
