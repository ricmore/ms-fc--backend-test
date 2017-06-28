package com.scmspain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DiscardedTweet {
  @Id
  private Long id;
  @Column(nullable = false)
  private String publisher;
  @Column(nullable = false, length = 2048)
  private String tweet;
  @Column (nullable=true)
  private Long pre2015MigrationStatus = 0L;

  public DiscardedTweet() {}

  public DiscardedTweet(Tweet tweet) {
    this.id = tweet.getId();
    this.publisher = tweet.getPublisher();
    this.tweet = tweet.getTweet();
    this.pre2015MigrationStatus = tweet.getPre2015MigrationStatus();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getTweet() {
    return tweet;
  }

  public void setTweet(String tweet) {
    this.tweet = tweet;
  }

  public Long getPre2015MigrationStatus() {
    return pre2015MigrationStatus;
  }

  public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
    this.pre2015MigrationStatus = pre2015MigrationStatus;
  }
}
