package com.scmspain.services;

import com.scmspain.entities.DiscardedTweet;
import com.scmspain.entities.Tweet;
import com.scmspain.repository.TweetRepository;

import javax.transaction.Transactional;

public class DiscardTweetService {
  private TweetRepository<Tweet, Long> mainRepository;
  private TweetRepository<DiscardedTweet, Long> discardedRepository;

  public DiscardTweetService(TweetRepository<Tweet, Long> mainRepository, TweetRepository<DiscardedTweet, Long> discardedRepository) {
    this.mainRepository = mainRepository;
    this.discardedRepository = discardedRepository;
  }

  /**
   * Discards a tweet from the main timeline to the discarded one
   * @param id
   */
  @Transactional
  public void discardTweet(Long id) {
    Tweet tweet = this.mainRepository.get(id);
    DiscardedTweet discardedTweet = new DiscardedTweet(tweet);

    this.discardedRepository.create(discardedTweet);
    this.mainRepository.delete(tweet);
  }
}
