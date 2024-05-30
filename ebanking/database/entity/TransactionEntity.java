package com.example.ebanking.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tbl_transaction")
public class TransactionEntity extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "sender_card_number")
    private String senderCardNumber;

    @Column(name = "recipient_card_number")
    private String recipientCardNumber;

    @Column(name = "amount")
    private double amount;

    @Column(name = "account_id")
    private UUID accountId;
}

