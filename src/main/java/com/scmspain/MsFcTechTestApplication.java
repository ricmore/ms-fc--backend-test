package com.scmspain;

import com.scmspain.configuration.InfrastructureConfiguration;
import com.scmspain.configuration.TweetConfiguration;
import com.scmspain.domain.repository.TweetRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@Import({TweetConfiguration.class, InfrastructureConfiguration.class})
@EnableJpaRepositories(basePackageClasses = TweetRepository.class)
@SpringBootApplication
public class MsFcTechTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsFcTechTestApplication.class, args);
    }
}
