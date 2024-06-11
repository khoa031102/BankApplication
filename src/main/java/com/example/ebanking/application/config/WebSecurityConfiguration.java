package com.example.ebanking.application.config;

import lombok.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, MessageSource messageSource) {
        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
        impl.setUserDetailsService(new InMemoryUserDetailsManager());
        impl.setMessageSource(messageSource);
        impl.setPasswordEncoder(passwordEncoder);
        impl.setHideUserNotFoundExceptions(true);
        return impl;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerExceptionResolver handlerExceptionResolver) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint((request, response, authException) -> handlerExceptionResolver.resolveException(request, response, null, authException))
                            .accessDeniedHandler((request, response, accessDeniedException) -> handlerExceptionResolver.resolveException(request, response, null, accessDeniedException));
                })
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/auth/**", "/api/dev/**", "/api/public/**", "/actuator/health", "/actuator/logfile").permitAll()
                            .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers("/**").permitAll();
                })
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOriginPatterns("*");
            }
        };
    }
}
