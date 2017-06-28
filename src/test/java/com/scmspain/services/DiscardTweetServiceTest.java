package com.scmspain.services;

import com.scmspain.entities.DiscardedTweet;
import com.scmspain.entities.Tweet;
import com.scmspain.repository.EMDiscardedTweetRepository;
import com.scmspain.repository.EMTweetRepository;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DiscardTweetServiceTest {
  private EntityManager entityManager;
  private DiscardTweetService discardTweetService;

  @Before
  public void setUp() throws Exception {
    this.entityManager = mock(EntityManager.class);
    this.discardTweetService = new DiscardTweetService(new EMTweetRepository(entityManager), new EMDiscardedTweetRepository(entityManager));
  }

  @Test
  public void shouldDiscardAnExistingTweet() throws Exception {
    Tweet tweet = new Tweet();
    tweet.setTweet("Example tweet");
    tweet.setPublisher("Some dudee");

    when(entityManager.find(Tweet.class, 123L)).thenReturn(tweet);

    discardTweetService.discardTweet(123L);

    verify(entityManager).persist(any(DiscardedTweet.class));
    verify(entityManager).remove(any(Tweet.class));
  }
}

