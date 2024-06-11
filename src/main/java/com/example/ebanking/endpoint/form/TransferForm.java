package com.example.ebanking.endpoint.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferForm {
    private String recipientCardNumber;
    private double amount;
}
