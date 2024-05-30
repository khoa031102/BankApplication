package com.example.ebanking.application.security;

import com.example.ebanking.database.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SpringUserService {
    private final AccountService accountService;

    public SpringUserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public SpringUser loadUser(UUID id) {
        return accountService.findById(id)
                .map(SpringUser::new)
                .orElse(null);
    }
}
