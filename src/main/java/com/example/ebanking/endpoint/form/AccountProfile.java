package com.example.ebanking.endpoint.form;

import com.example.ebanking.database.constant.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AccountProfile {

    @NotBlank
    @Length(max = ValidationConstants.FULL_NAME_MAX_LENGTH)
    private String name;

    @Email
    @NotBlank
    @Length(max = ValidationConstants.EMAIL_MAX_LENGTH)
    private String email;

    @NotBlank
    private String cardNumber;

}
