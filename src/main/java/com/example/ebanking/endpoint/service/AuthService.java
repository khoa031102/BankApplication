package com.example.ebanking.endpoint.service;

import com.example.ebanking.application.security.JwtTokenAccess;
import com.example.ebanking.application.security.JwtTokenProvider;
import com.example.ebanking.database.constant.ActiveStatus;
import com.example.ebanking.database.dto.AccountDto;
import com.example.ebanking.database.entity.AccountEntity;
import com.example.ebanking.database.service.AccountService;
import com.example.ebanking.endpoint.form.AccountCreateForm;
import com.example.ebanking.endpoint.form.AccountLoginForm;
import com.example.ebanking.endpoint.form.AccountUpdateForm;
import com.example.ebanking.util.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountService accountService;

    public AuthService(JwtTokenProvider jwtTokenProvider, AccountService accountService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountService = accountService;
    }

    public AccountEntity validateAccountLogin(AccountLoginForm form) {
        AccountEntity account = accountService.findByLogin(form.getAccount())
                .orElseGet(() -> accountService.findByEmail(form.getAccount())
                        .orElseThrow(() -> RestApiException.badRequest("Exception.AccountLoginNotFound", form.getAccount())));

        boolean verifyPassword = accountService.verifyPassword(form.getPassword(), account.getPassword());
        if (!verifyPassword)
            throw RestApiException.badRequest("Exception.AccountPasswordInvalid");

        if (!account.getStatus().equals(ActiveStatus.ACTIVE))
            throw RestApiException.badRequest("Exception.AccountStatusInactive");

        return account;
    }

    public JwtTokenAccess login(AccountLoginForm form) {
        AccountEntity account = validateAccountLogin(form);

        JwtTokenAccess accessToken = jwtTokenProvider.createAccessToken(account, true);
        log.info("Account " + account.getId() + " | " + account.getLogin());
        log.info("Bearer " + accessToken.getToken());

        return accessToken;
    }

    public AccountDto registration(AccountCreateForm form) {
        AccountEntity account = accountService.create(form.getLogin(), form.getEmail(), form.getName(), form.getPassword());
        return AccountDto.fromEntity(account);
    }
}
