package com.example.transactionchallenge.domain.repository;

import com.example.transactionchallenge.domain.entity.v2.AccountV2;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountV2Repository extends JpaRepository<AccountV2, Long> {

    @Transactional
    @Query("select c.balance from AccountV2 c where c.id = :accountId")
    public double getBalance(Long accountId);


    // pessimistic write!
    // the first win, others receive a PessimistLockException
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from AccountV2 c where c.id = :accountId")
    public Optional<AccountV2> findByIdWithPessimisticLocking(Long accountId);

    // AtomicOperation (only 1 trip to database)
    // this needs to have a check constrait in teh database
    // because we can have negative values
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update account set balance = (balance - :amount) where " +
                    "id = :accountId")
    public int updateAndSubtractFromBalance(Long accountId, Double amount);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update account set balance = (balance + :amount) where " +
                    "id = :accountId")
    public int updateAndAddToBalance(Long accountId, Double amount);
}
