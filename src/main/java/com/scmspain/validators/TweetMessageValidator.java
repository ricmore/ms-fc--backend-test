package com.scmspain.validators;

import com.scmspain.entities.Tweet;

public class TweetMessageValidator implements Validator<Tweet> {
  @Override
  public void validate(Tweet tweet) throws IllegalArgumentException {
    String text = tweet.getTweet();

    if (text == null || removeLinks(text).length() <= 0 || removeLinks(text).length() > 140) {
      throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
    }
  }

  private String removeLinks(String text) {
    return text.replaceAll("https?://[^ \n]+", "");
  }
}
