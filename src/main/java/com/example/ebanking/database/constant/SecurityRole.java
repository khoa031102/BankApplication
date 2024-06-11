package com.example.ebanking.database.constant;

import com.example.ebanking.application.security.SpringConstants;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum SecurityRole implements EnumDescription {
    SYSTEM(SpringConstants.Authorities.ROLE_SYSTEM_LEVEL, SpringConstants.Authorities.ROLE_SYSTEM),
    ADMIN(SpringConstants.Authorities.ROLE_ADMIN_LEVEL, SpringConstants.Authorities.ROLE_ADMIN),
    USER(SpringConstants.Authorities.ROLE_USER_LEVEL, SpringConstants.Authorities.ROLE_USER);

    private final int level;
    private final GrantedAuthority authority;

    SecurityRole(int level, String authority) {
        this.level = level;
        this.authority = new SimpleGrantedAuthority(authority);
    }

    public static SecurityRole tryParse(String name) {
        try {
            return SecurityRole.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
