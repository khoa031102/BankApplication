package com.example.ebanking.database.dto;

import com.example.ebanking.database.constant.ActiveStatus;
import com.example.ebanking.database.constant.SecurityRole;
import com.example.ebanking.database.entity.AccountEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AccountDto extends AbstractAuditingDto {
    private UUID id;
    private String login;
    private String email;
    private String name;
    private String cardNumber;
    private SecurityRole role;
    private ActiveStatus status;
    private UUID managerId;

    public static AccountDto fromEntity(AccountEntity entity) {
        AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setCardNumber(entity.getCardNumber());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setManagerId(entity.getManagerId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        return dto;
    }
}
