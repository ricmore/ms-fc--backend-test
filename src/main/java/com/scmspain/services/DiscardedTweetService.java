package com.scmspain.services;

import com.scmspain.entities.DiscardedTweet;
import com.scmspain.repository.TweetRepository;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import java.util.List;

public class DiscardedTweetService {
  private TweetRepository<DiscardedTweet, Long> repository;
  private MetricWriter metricWriter;

  public DiscardedTweetService(TweetRepository repository, MetricWriter metricWriter) {
    this.repository = repository;
    this.metricWriter = metricWriter;
  }

  public List<DiscardedTweet> listAllTweets() {
    return this.repository.getAll();
  }
}
