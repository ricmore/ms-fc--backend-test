package com.scmspain.repository;

import com.scmspain.entities.DiscardedTweet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EMDiscardedTweetRepository implements TweetRepository<DiscardedTweet, Long> {
  private EntityManager entityManager;

  public EMDiscardedTweetRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void create(DiscardedTweet tweet) {
    this.entityManager.persist(tweet);
  }

  @Override
  public void delete(DiscardedTweet tweet) {
    this.entityManager.remove(tweet);
  }

  @Override
  public DiscardedTweet get(Long id) {
    return this.entityManager.find(DiscardedTweet.class, id);
  }

  @Override
  public List<DiscardedTweet> getAll() {
    List<DiscardedTweet> result = new ArrayList<DiscardedTweet>();

    TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM DiscardedTweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC", Long.class);
    List<Long> ids = query.getResultList();
    for (Long id : ids) {
      result.add(get(id));
    }
    return result;
  }
}
