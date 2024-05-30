package com.example.ebanking.database.dto;

import com.example.ebanking.database.entity.TransactionEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionDto extends AbstractAuditingDto {
    private UUID id;
    private String senderCardNumber;
    private String recipientCardNumber;
    private double amount;
    private UUID accountId;

    public static TransactionDto fromEntity(TransactionEntity entity) {
        TransactionDto dto = new TransactionDto();
        dto.setId(entity.getId());
        dto.setSenderCardNumber(entity.getSenderCardNumber());
        dto.setRecipientCardNumber(entity.getRecipientCardNumber());
        dto.setAmount(entity.getAmount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        return dto;
    }
}
