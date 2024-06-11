package com.example.ebanking.endpoint.form;

import com.example.ebanking.database.constant.ValidationConstants;
import com.example.ebanking.database.entity.AbstractSerializable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AccountCreateForm extends AbstractSerializable {
    @NotBlank
    @Length(min = 3, max = 50)
    private String login;

    @Email
    @NotBlank
    @Length(max = ValidationConstants.EMAIL_MAX_LENGTH)
    private String email;

    @NotBlank
    @Length(max = ValidationConstants.FULL_NAME_MAX_LENGTH)
    private String name;

    @NotBlank
    @Length(min = ValidationConstants.PASSWORD_MIN_LENGTH, max = ValidationConstants.PASSWORD_MAX_LENGTH)
    private String password;
}
