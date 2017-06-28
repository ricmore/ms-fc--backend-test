package com.scmspain.configuration;

import com.scmspain.controller.TweetController;
import com.scmspain.repository.EMTweetRepository;
import com.scmspain.repository.TweetRepository;
import com.scmspain.services.TweetService;
import com.scmspain.validators.TweetValidator;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class TweetConfiguration {
    @Bean
    public EMTweetRepository getTweetRepository(EntityManager entityManager) {
        return new EMTweetRepository(entityManager);
    }

    @Bean
    public TweetService getTweetService(TweetRepository tweetRepository, MetricWriter metricWriter, TweetValidator tweetValidator) {
        return new TweetService(tweetRepository, metricWriter, tweetValidator);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }
}
