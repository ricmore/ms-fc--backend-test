package com.scmspain.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for a tweet
 *
 * @author ricardmore
 */
// TODO this clas must be in a external artifact so that could be reused by any Java client.
public final class TweetDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("tweet")
    private String tweet;

    @JsonCreator
    public TweetDTO(@JsonProperty("id") Long id, @JsonProperty("publisher") String publisher, @JsonProperty("tweet") String tweet) {
        this.id = id;
        this.publisher = publisher;
        this.tweet = tweet;
    }

    public Long getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTweet() {
        return tweet;
    }
}
