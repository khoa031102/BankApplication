package com.example.ebanking.database.service;

import com.example.ebanking.database.entity.AccountEntity;
import com.example.ebanking.database.entity.TransactionEntity;
import com.example.ebanking.database.repository.TransactionEntityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionEntityRepository repository;

    public TransactionService(TransactionEntityRepository repository) {
        this.repository = repository;
    }

    public TransactionEntity create(String senderCardNumber, String recipientCardNumber, double amount, UUID accountId) {
        TransactionEntity transaction = new TransactionEntity();
        AccountEntity account = new AccountEntity();
        transaction.setSenderCardNumber(senderCardNumber);
        transaction.setRecipientCardNumber(recipientCardNumber);
        transaction.setAmount(amount);
        transaction.setAccountId(accountId);
        return repository.save(transaction);
    }
}
