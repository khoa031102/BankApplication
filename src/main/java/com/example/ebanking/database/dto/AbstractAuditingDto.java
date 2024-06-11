package com.example.ebanking.database.dto;

import com.example.ebanking.database.entity.AbstractSerializable;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@Getter
@Setter
public abstract class AbstractAuditingDto extends AbstractSerializable {
    protected Instant createdDate;
    protected Instant lastModifiedDate;
}
