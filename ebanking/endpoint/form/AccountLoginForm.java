package com.example.ebanking.endpoint.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginForm {
    @NotBlank
    private String account;
    @NotBlank
    private String password;
}
