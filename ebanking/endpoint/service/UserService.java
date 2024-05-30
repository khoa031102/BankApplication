package com.example.ebanking.endpoint.service;

import com.example.ebanking.application.security.SpringUser;
import com.example.ebanking.database.dto.AccountDto;
import com.example.ebanking.database.dto.TransactionDto;
import com.example.ebanking.database.entity.AccountEntity;
import com.example.ebanking.database.entity.TransactionEntity;
import com.example.ebanking.database.service.AccountService;
import com.example.ebanking.database.service.TransactionService;
import com.example.ebanking.endpoint.form.AccountUpdateForm;
import com.example.ebanking.endpoint.form.TransferForm;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final TransactionService transactionService;
    private final AccountService accountService;

    public UserService(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public TransactionDto transfer(TransferForm form, SpringUser springUser) {
        AccountEntity account = springUser.getAccountEntity();

        if (account != null) {
            TransactionEntity entity = transactionService.create(account.getCardNumber(), form.getRecipientCardNumber(), form.getAmount(), account.getId());
            return TransactionDto.fromEntity(entity);
        }

        return null;
    }

    public AccountDto find(String cardNumber) {
        AccountEntity account = accountService.findByCardNumber(cardNumber).orElse(null);
        if (account != null) {
            return AccountDto.fromEntity(account);
        }
        return null;
    }

    public AccountDto update(UUID id, AccountUpdateForm form) {
        AccountEntity account = accountService.update(id, form);
        return AccountDto.fromEntity(account);
    }

    public AccountDto delete(UUID id) {
        AccountEntity account = accountService.delete(id);
        return AccountDto.fromEntity(account);
    }

    public void changePassword(SpringUser springUser, String newPassword) {
        accountService.changePassword(springUser.getAccountEntity(), springUser.getPassword(), newPassword);
    }
}
