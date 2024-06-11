package com.example.ebanking.endpoint.controller;

import com.example.ebanking.application.security.JwtTokenAccess;
import com.example.ebanking.database.dto.AccountDto;
import com.example.ebanking.database.entity.AccountEntity;
import com.example.ebanking.endpoint.form.AccountCreateForm;
import com.example.ebanking.endpoint.form.AccountLoginForm;
import com.example.ebanking.endpoint.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "I. AuthController")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(description = "1. Register your bank account")
    @PostMapping("/sign-up")
    public AccountDto registration(@Valid @RequestBody AccountCreateForm form) {
        return authService.registration(form);
    }

    @Operation(description = "2. Log in to your account")
    @PostMapping("/sign-in")
    public JwtTokenAccess login(@Valid @RequestBody AccountLoginForm form) {
        return authService.login(form);
    }
}
