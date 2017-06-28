package com.scmspain.repository;

import com.scmspain.entities.Tweet;

import java.util.List;

public interface TweetRepository {
  public void create(Tweet tweet);
  public Tweet get(Long id);
  public List<Tweet> getAll();
}
