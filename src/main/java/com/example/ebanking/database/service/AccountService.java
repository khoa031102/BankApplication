package com.example.ebanking.database.service;

import com.example.ebanking.database.constant.ActiveStatus;
import com.example.ebanking.database.constant.SecurityRole;
import com.example.ebanking.database.entity.AccountEntity;
import com.example.ebanking.database.repository.AccountEntityRepository;
import com.example.ebanking.endpoint.form.AccountUpdateForm;
import com.example.ebanking.util.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AccountService extends AbstractEntityService<AccountEntity, UUID, AccountEntityRepository> {
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountEntityRepository repository,
                          PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AccountEntity create(String login, String email, String name, String rawPassword) {
        if (existsByEmailNotBlank(email))
            throw RestApiException.badRequest("Exception.AccountEmailExists", email);
        AccountEntity account = new AccountEntity();
        account.setLogin(login);
        account.setEmail(StringUtils.hasText(email) ? email : null);
        account.setName(name);
        account.setCardNumber(generateCardNumber());
        account.setStatus(ActiveStatus.ACTIVE);
        account.setPassword(encodePassword(rawPassword));
        account.setRole(SecurityRole.USER);
        account.setManagerId(null);
        return save(account);
    }

    @Transactional
    public AccountEntity update(UUID id, AccountUpdateForm form) {
        AccountEntity account = findById(id).orElseThrow(() -> RestApiException.badRequest("Exception.AccountNotFound"));
        if (!Objects.equals(form.getEmail(), account.getEmail()) && existsByEmailNotBlank(form.getEmail()))
            throw RestApiException.badRequest("Exception.AccountEmailExists", form.getEmail());
        account.setEmail(form.getEmail());
        account.setName(form.getName());
        account.setStatus(form.getStatus());
        if (StringUtils.hasText(form.getPassword()))
            account.setPassword(encodePassword(form.getPassword()));
        return save(account);
    }

    @Transactional
    public AccountEntity delete(UUID id) {
        AccountEntity account = findById(id).orElseThrow(() -> RestApiException.badRequest("Exception.AccountNotFound"));
        if (account.getStatus() == ActiveStatus.ACTIVE)
            throw RestApiException.badRequest("Exception.AccountStatusActive");
        return delete(account);
    }

    @Transactional
    public void changePassword(AccountEntity account, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, account.getPassword()))
            throw RestApiException.badRequest("Exception.AccountPasswordInvalid");
        String passwordHash = passwordEncoder.encode(newPassword);
        account.setPassword(passwordHash);
        save(account);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Optional<AccountEntity> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<AccountEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<AccountEntity> findByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber);
    }

    public boolean existsByEmailNotBlank(String email) {
        return StringUtils.hasText(email) && repository.existsByEmail(email);
    }

    private String generateCardNumber() {
        int random = (int) (Math.random() * 1000);
        return "9999" + String.format("%03d", random);
    }
}
