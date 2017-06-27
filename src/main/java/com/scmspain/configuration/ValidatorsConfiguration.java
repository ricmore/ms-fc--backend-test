package com.scmspain.configuration;

import com.scmspain.validators.TweetMessageValidator;
import com.scmspain.validators.TweetPublisherValidator;
import com.scmspain.validators.TweetValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ValidatorsConfiguration {
  @Bean
  public TweetValidator tweetValidator(TweetMessageValidator tweetMessageValidator, TweetPublisherValidator tweetPublisherValidator) {
    return new TweetValidator(Arrays.asList(tweetPublisherValidator, tweetMessageValidator));
  }

  @Bean
  public TweetMessageValidator tweetMessageValidator() {
    return new TweetMessageValidator();
  }

  @Bean
  public TweetPublisherValidator tweetPublisherValidator() {
    return new TweetPublisherValidator();
  }
}
