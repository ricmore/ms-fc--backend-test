package com.scmspain.services;

import com.scmspain.entities.DiscardedTweet;
import com.scmspain.repository.TweetRepository;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

public class DiscardedTweetService {
  private TweetRepository<DiscardedTweet, Long> repository;
  private MetricWriter metricWriter;

  public DiscardedTweetService(TweetRepository repository, MetricWriter metricWriter) {
    this.repository = repository;
    this.metricWriter = metricWriter;
  }
}
