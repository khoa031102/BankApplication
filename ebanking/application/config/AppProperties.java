package com.example.ebanking.application.config;

import com.example.ebanking.database.entity.AbstractSerializable;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;

@Slf4j
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties extends AbstractSerializable {
    @NotNull
    private URI url;
    @NotNull
    private String name;
    @NotNull
    private String version;
    @NotNull
    private Path storage;
    @NotNull
    private JwtToken jwtToken;

    @Getter
    @Setter
    public static class JwtToken {
        @NotNull
        private String secret;
        private Duration validity = Duration.ofHours(1);
        private Duration remember = Duration.ofDays(1);
    }

    @PostConstruct
    private void onPostConstruct() {
        log.info(this.toJson());
    }
}
