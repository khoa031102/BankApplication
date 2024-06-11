package com.example.ebanking.endpoint.controller;

import com.example.ebanking.application.security.CurrentSpringUser;
import com.example.ebanking.application.security.SpringUser;
import com.example.ebanking.database.dto.AccountDto;
import com.example.ebanking.database.dto.TransactionDto;
import com.example.ebanking.endpoint.form.AccountChangePasswordForm;
import com.example.ebanking.endpoint.form.AccountUpdateForm;
import com.example.ebanking.endpoint.form.TransferForm;
import com.example.ebanking.endpoint.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "II. UserController")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "3. Current account")
    @GetMapping("/account")
    public AccountDto current(@CurrentSpringUser SpringUser springUser) {
        return AccountDto.fromEntity(springUser.getAccountEntity());
    }

    @Operation(description = "4. Find out the recipient account")
    @GetMapping("/account/{cardNumber}")
    public AccountDto find(@PathVariable String cardNumber) {
        return userService.find(cardNumber);
    }

    @Operation(description = "5. Resolve transaction")
    @PostMapping("/transfer")
    public TransactionDto transfer(@Valid @RequestBody TransferForm form, @CurrentSpringUser SpringUser springUser) {
        return userService.transfer(form, springUser);
    }

    //NOTE: Optional
    @Operation(description = "Update account information")
    @PutMapping("/account")
    public AccountDto update(@Valid @RequestBody AccountUpdateForm form, @CurrentSpringUser SpringUser springUser) {
        return userService.update(springUser.getAccountEntity().getId(), form);
    }

    @Operation(description = "Delete account information")
    @DeleteMapping("/account")
    public AccountDto delete(@CurrentSpringUser SpringUser springUser) {
        return userService.delete(springUser.getAccountEntity().getId());
    }

    @Operation(description = "Change password of account")
    @DeleteMapping("/account/{id}")
    public void changePassword(@Valid @RequestBody AccountChangePasswordForm form, @CurrentSpringUser SpringUser springUser) {
        userService.changePassword(springUser, form.getPassword());
    }

    @GetMapping("/profile")
    public AccountDto profiledetail(@CurrentSpringUser SpringUser springUser) {
        return AccountDto.fromEntity(springUser.getAccountEntity());
    }

    @GetMapping("/detail")
    public AccountDto detail(@CurrentSpringUser SpringUser springUser) {
        return AccountDto.fromEntity(springUser.getAccountEntity());
    }
}
