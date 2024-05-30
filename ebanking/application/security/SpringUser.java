package com.example.ebanking.application.security;

import com.example.ebanking.database.constant.ActiveStatus;
import com.example.ebanking.database.entity.AccountEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class SpringUser implements UserDetails {

    private final AccountEntity accountEntity;

    private final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonLocked;

    public SpringUser(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
        this.username = accountEntity.getLogin();
        this.password = accountEntity.getPassword();
        this.enabled = accountEntity.getStatus() == ActiveStatus.ACTIVE;
        this.accountNonLocked = accountEntity.getStatus() != ActiveStatus.DISABLED;
        this.grantedAuthorities.add(accountEntity.getRole().getAuthority());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
