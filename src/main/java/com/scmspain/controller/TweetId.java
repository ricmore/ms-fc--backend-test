package com.scmspain.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Stores tweet id.
 *
 * @author ricardmore
 */
public final class TweetId implements Serializable {

    @JsonProperty("tweet")
    private Long tweet;

    @JsonCreator
    public TweetId(@JsonProperty("tweet") Long tweet) {
        this.tweet = tweet;
    }

    public Long getTweet() {
        return tweet;
    }
}
