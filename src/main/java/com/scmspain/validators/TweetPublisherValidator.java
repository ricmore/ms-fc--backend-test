package com.scmspain.validators;

import com.scmspain.entities.Tweet;

public class TweetPublisherValidator implements Validator<Tweet> {
  @Override
  public void validate(Tweet tweet) throws IllegalArgumentException {
    String publisher = tweet.getPublisher();

    if (publisher == null || publisher.length() <= 0 ) {
      throw new IllegalArgumentException("Publisher must not be empty");
    }
  }
}
