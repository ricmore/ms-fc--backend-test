package com.scmspain.validators;

import com.scmspain.entities.Tweet;

import java.util.Collection;

public class TweetValidator implements Validator<Tweet> {
  private Collection<Validator<Tweet>> validators;

  public TweetValidator(Collection<Validator<Tweet>> validators) {
    this.validators = validators;
  }

  @Override
  public void validate(Tweet tweet) throws IllegalArgumentException {
    this.validators.forEach(tweetValidator -> tweetValidator.validate(tweet));
  }
}
