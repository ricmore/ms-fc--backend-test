package com.scmspain.configuration;

import com.scmspain.controller.TweetController;
import com.scmspain.repository.EMDiscardedTweetRepository;
import com.scmspain.repository.EMTweetRepository;
import com.scmspain.services.DiscardTweetService;
import com.scmspain.services.TweetService;
import com.scmspain.validators.TweetValidator;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class TweetConfiguration {
    @Bean
    public EMDiscardedTweetRepository getDiscardedTweetRepository(EntityManager entityManager) {
        return new EMDiscardedTweetRepository(entityManager);
    }

    @Bean
    public EMTweetRepository getTweetRepository(EntityManager entityManager) {
        return new EMTweetRepository(entityManager);
    }

    @Bean
    public DiscardTweetService getDiscardTweetService(EMTweetRepository emTweetRepository, EMDiscardedTweetRepository emDiscardedTweetRepository) {
        return new DiscardTweetService(emTweetRepository, emDiscardedTweetRepository);
    }

    @Bean
    public TweetService getTweetService(EMTweetRepository tweetRepository, MetricWriter metricWriter, TweetValidator tweetValidator) {
        return new TweetService(tweetRepository, metricWriter, tweetValidator);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService, DiscardTweetService discardTweetService) {
        return new TweetController(tweetService, discardTweetService);
    }
}
