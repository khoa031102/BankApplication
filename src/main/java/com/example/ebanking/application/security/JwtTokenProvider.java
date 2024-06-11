package com.example.ebanking.application.security;

import com.example.ebanking.application.config.AppProperties;
import com.example.ebanking.database.entity.AccountEntity;
import com.example.ebanking.util.RestApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Configuration
public class JwtTokenProvider {
    public static final String AUTH_TOKEN_TYPE = "Bearer";

    private final AppProperties appProperties;
    private final SpringUserService springUserService;

    public JwtTokenProvider(AppProperties appProperties, SpringUserService springUserService) {
        this.appProperties = appProperties;
        this.springUserService = springUserService;
    }

    public void resolveAndValidateToken(HttpServletRequest request) {
        String token = resolveToken(request);
        if (StringUtils.hasText(token) && !"null".equals(token)) {
            String jwtSecret = appProperties.getJwtToken().getSecret();

            Claims claims = validateToken(token, jwtSecret);
            if (claims != null) {
                UUID id = UUID.fromString(claims.getId());
                SpringUser springUser = springUserService.loadUser(id);
                if (springUser != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(springUser, token, springUser.getAuthorities());
                    if (!springUser.isEnabled()) {
                        authentication.setAuthenticated(false);
                    }
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw RestApiException.unauthorized("Exception.SessionNotFound");
                }
            }
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(AUTH_TOKEN_TYPE)) {
            return token.substring(AUTH_TOKEN_TYPE.length() + 1);
        }
        return null;
    }

    public Claims validateToken(String token, String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("ValidateTokenException: {}", e.getMessage());
            return null;
        }
    }

    public JwtTokenAccess createAccessToken(AccountEntity account, boolean remember) {
        AppProperties.JwtToken jwtToken = appProperties.getJwtToken();

        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(remember ? jwtToken.getRemember() : jwtToken.getValidity());

        String token = Jwts.builder()
                .setId(account.getId().toString())
                .setSubject(account.getName())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .setAudience(account.getRole().name())
                .signWith(SignatureAlgorithm.HS512, jwtToken.getSecret())
                .compact();

        JwtTokenAccess accessToken = new JwtTokenAccess();
        accessToken.setTokenId(account.getId().toString());
        accessToken.setSubject(account.getName());
        accessToken.setExpiresAt(expiration);
        accessToken.setToken(token);
        accessToken.setType(AUTH_TOKEN_TYPE);
        accessToken.setRole(account.getRole().toString());
        return accessToken;
    }
}
