package com.example.ebanking.application.config;

import com.example.ebanking.application.security.CurrentSpringUser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Slf4j
@Configuration
public class OpenAPIConfiguration {
    public static final String SECURITY_SCHEMA_NAME = "JWT";
    private final AppProperties appProperties;

    public OpenAPIConfiguration(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        SpringDocUtils.getConfig()
                .addAnnotationsToIgnore(CurrentSpringUser.class);

        Info info = new Info()
                .title(appProperties.getName())
                .version(appProperties.getVersion());

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .name(HttpHeaders.AUTHORIZATION)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components();
        components.addSecuritySchemes(SECURITY_SCHEMA_NAME, securityScheme);

        return new OpenAPI(SpecVersion.V31)
                .info(info)
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA_NAME));
    }

    @Bean
    public GroupedOpenApi apiDocuments() {
        return GroupedOpenApi.builder()
                .group("API-Documents")
                .displayName("API-Documents")
                .pathsToMatch("/api/**")
                .build();
    }

    @PostConstruct
    protected void onPostConstruct() {
        log.info("Initialized OpenAPIConfiguration");
    }
}
