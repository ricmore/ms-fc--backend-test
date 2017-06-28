package com.scmspain.repository;

import com.scmspain.entities.Tweet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EMTweetRepository implements TweetRepository<Tweet, Long> {
  private EntityManager entityManager;

  public EMTweetRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void create(Tweet tweet) {
    this.entityManager.persist(tweet);
  }

  @Override
  public void delete(Tweet tweet) {
    this.entityManager.remove(tweet);
  }

  @Override
  public Tweet get(Long id) {
    return this.entityManager.find(Tweet.class, id);
  }

  @Override
  public List<Tweet> getAll() {
    List<Tweet> result = new ArrayList<Tweet>();

    TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC", Long.class);
    List<Long> ids = query.getResultList();
    for (Long id : ids) {
      result.add(get(id));
    }
    return result;
  }
}
