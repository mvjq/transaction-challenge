package com.example.transactionchallenge.service;

import com.example.transactionchallenge.TransactionFacadeImpl;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.domain.DomainConverter;
import com.example.transactionchallenge.domain.entity.Transaction;
import com.example.transactionchallenge.domain.repository.AccountRepository;
import com.example.transactionchallenge.domain.repository.AccountV2Repository;
import com.example.transactionchallenge.domain.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountV2Repository accountV2Repository;
    private final DomainConverter converter;
    private final AccountService accountService;

    // for the advisory lock
    // private LockManager lockManager;
    // this lock manager is a class which trys to lock based on a lock key
    // needs to configure the retryTemplate because if i ask
    // for a lock, i need to retry until i get it

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountV2Repository accountV2Repository, DomainConverter converter, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountV2Repository = accountV2Repository;
        this.converter = converter;
        this.accountService = accountService;
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {

        var account =
                accountRepository.findById(transactionRequest.accountId())
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND));
        var transaction = converter.toEntity(transactionRequest, account);

        if (transaction.getOperationType().isNegativeAmount()) {
            // subtract from account
        } else if (!transaction.getOperationType().isNegativeAmount()) {
            // add to account
        }

        return transactionRepository.save(transaction);
    }
    /*
    Discussion
     Here i use operation type as if statement, but because its a enum
     i can do so much better. This branching is more imperative, a good
     alternative is using OOP/polymorfism in the following structure
     abstract class OperationType
        doOperation(Transaction t, Money amount)

     // the three following are negative!
     public class WithdrawOperationType extends OperationType
     public class PurchaseWithInstallmentOperationType extends OperationType
     public class PurchaseOperationType extends OperationType

     public class CreditVoucherOperationType extends OperationType (positive)
     and then on the factory/bvuider/construtor of transaction i do the
     resolution for the right object (or a factory inside a object? hmm)
     and then here a just do "doOperation()" and voila
     */

    private void subtractFromAccount(Transaction transaction) {
        atomicUpdateNegativeOperation(transaction);
    }

    private void addToAccount(Transaction transaction) {

    }

    @Transactional
    // ths methnod needs to be updated to transactioonV2 and accountV2
    public void atomicUpdateNegativeOperation(Transaction transaction) {

        var curBalance =
                accountV2Repository.updateAndSubtractFromBalance(transaction.getAccount().getId(), transaction.getAmount());

        // this if is because right now i dont have a check constraint inside
        // posgres
        // to check balance > 0
        if (curBalance < transaction.getAmount()) {
            throw new IllegalArgumentException("Amount can't be negative");
        }

        // save transaction
        transactionRepository.save(transaction);
    }

    @Transactional
    public void CheckConstraintNegativeOperation(Transaction transaction) {
        // same code than above
        // this in the DDL, we have this
        // ALTER TABLE accountV2 ADD CONSTRAINT balance_check CHECK (balance
        // >= 0)
    }
    // because is optimisci lock
    // we need to handle the erros
    // a @Retryable is welcome!
    // retryable needs to imported as dependency
    // spring-retry

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100, random = true, multiplier = 2.0)
    )
    private void OptimisticLockNegativeOperation(Transaction transaction) {

        // the account objects needs to have a @Version annotation
        // and a Long version column/field so in the
        var account =
                accountRepository.findById(transaction.getAccount().getId())
                        .orElseThrow(); // especify the excception here


        //
        // the balance <0 checks needs to exist (no constraint in the bank)
        // set new balance, save account, create and save transaction, yada
        // yada
    }


    // in posgres we have a "lock manager", which is simillar to redis locking
    // but is managed by the bank
    private void AdvisoryLockNegativeOperation() {
        // here we use the lockManager
        // like key = account, timetout (seconds, 3 ex.)
        // lockmanager(key, timeout () -> {});
        // this is similar to a distributed redis locking
    }

    // another solution (which i find that is hard and worst, but somehow its
    // solve the problem
    // its changing the transaction isolation level to SERIALIZABLE

    @Override
    public Transaction getTransaction(Long id) {
        return null;
    }
}
