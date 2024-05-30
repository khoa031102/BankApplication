package com.example.ebanking;

import com.example.ebanking.application.config.AppProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
@EnableJpaAuditing
@EnableJpaRepositories
public class EBankingApplication {
	private final AppProperties appProperties;
	private final Environment environment;

    public EBankingApplication(AppProperties appProperties, Environment environment) {
        this.appProperties = appProperties;
        this.environment = environment;
    }

    public static void main(String[] args) {
		SpringApplication.run(EBankingApplication.class, args);
	}

	@PostConstruct
	private void onPostConstruct() {
		String protocol = "http";
		if (environment.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String serverPort = environment.getProperty("server.port");
		String contextPath = environment.getProperty("server.servlet.context-path");
		if (!StringUtils.hasText(contextPath)) {
			contextPath = "/";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host accountName could not be determined, using `localhost` as fallback");
		}

		String localUrl = protocol + "://" + hostAddress + ":" + serverPort + contextPath;
		String deployUrl = environment.getProperty("server.deploy");
		log.info(
				"\n----------------------------------------------------------\n" +
						"\tApplication: \t{}-v{}\n" +
						"\tLocal URL: \t\t{}\n" +
						"\tDeploy URL: \t{}\n" +
						"\tStorage: \t\t{}\n" +
						"\tProfile(s): \t{}\n----------------------------------------------------------",
				appProperties.getName(),
				appProperties.getVersion(),
				localUrl,
				appProperties.getUrl(),
				appProperties.getStorage().toAbsolutePath(),
				environment.getActiveProfiles()
		);
	}
}
