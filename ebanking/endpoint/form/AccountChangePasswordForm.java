package com.example.ebanking.endpoint.form;

import com.example.ebanking.database.constant.ValidationConstants;
import com.example.ebanking.database.entity.AbstractSerializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AccountChangePasswordForm extends AbstractSerializable {
    @Length(max = ValidationConstants.PASSWORD_MAX_LENGTH)
    private String password;
}
