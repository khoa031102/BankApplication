package com.example.ebanking.database.repository;

import com.example.ebanking.database.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<AccountEntity> findByLogin(String login);

    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByCardNumber(String cardNumber);
}
