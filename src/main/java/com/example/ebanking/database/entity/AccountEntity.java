package com.example.ebanking.database.entity;

import com.example.ebanking.database.constant.ActiveStatus;
import com.example.ebanking.database.constant.SecurityRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tbl_account", uniqueConstraints = @UniqueConstraint(columnNames = {"login, card_number"}))
public class AccountEntity extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "login", length = 200, nullable = false)
    private String login;

    @Column(name = "email", length = 200, unique = true)
    private String email;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "card_number", length = 12)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50, nullable = false)
    private SecurityRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private ActiveStatus status;

    @Column(name = "manager_id", updatable = false)
    private UUID managerId;
}
