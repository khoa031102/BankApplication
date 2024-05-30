package com.example.ebanking.endpoint.form;

import com.example.ebanking.database.constant.ActiveStatus;
import com.example.ebanking.database.constant.ValidationConstants;
import com.example.ebanking.database.entity.AbstractSerializable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AccountUpdateForm extends AbstractSerializable {
    @Email
    @Length(max = ValidationConstants.EMAIL_MAX_LENGTH)
    private String email;

    @NotBlank
    @Length(max = ValidationConstants.FULL_NAME_MAX_LENGTH)
    private String name;

    @Length(max = ValidationConstants.PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    private ActiveStatus status;
}
