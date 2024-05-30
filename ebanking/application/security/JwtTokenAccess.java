package com.example.ebanking.application.security;

import com.example.ebanking.database.entity.AbstractSerializable;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class JwtTokenAccess extends AbstractSerializable {
    private String tokenId;
    private String subject;
    private String type;
    private String token;
    private Instant expiresAt;
    private String role;

    public String toJsonWithoutToken() {
        JwtTokenAccess tokenAccess = new JwtTokenAccess();
        tokenAccess.setTokenId(tokenId);
        tokenAccess.setSubject(subject);
        tokenAccess.setExpiresAt(expiresAt);
        tokenAccess.setToken(null);
        tokenAccess.setType(type);
        return tokenAccess.toJson();
    }
}
