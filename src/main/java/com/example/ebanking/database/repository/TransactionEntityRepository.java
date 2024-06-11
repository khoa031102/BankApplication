package com.example.ebanking.database.repository;

import com.example.ebanking.database.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByAccountId(UUID accountId);
}
