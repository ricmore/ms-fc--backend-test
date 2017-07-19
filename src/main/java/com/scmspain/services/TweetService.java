package com.scmspain.services;

import com.scmspain.domain.entities.Tweet;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Tweet's service defining all the operations can be done over tweets.
 *
 * @author ricardmore
 */
public interface TweetService {

    /**
     * Push tweet to repository. Saves the tweet to domain level.
     *
     * @param publisher publisher.
     * @param tweet Tweet text beign published.
     *
     * @return Tweet once published and stored.
     */
    Tweet publishTweet(String publisher, String tweet) throws InvalidArgumentException;

    /**
     * Recover tweet from domain
     *
     * @param id id of the Tweet to retrieve
     * @return retrieved Tweet if the id exist
     */
    Optional<Tweet> getTweet(Long id);

    /**
     * Recover all the tweets from the domain, excluding the discarded ones.
     *
     * @return Paged list of tweets
     */
    Page<Tweet> listAllTweets(Pageable page);

    /**
     * Mark a tweet as discarded.
     *
     * @param tweetId Tweet unique identifier.
     * @return Tweet after beign discarded or null if the tweet doesn't exist.
     */
    Tweet discardTweet(Long tweetId);

    /**
     * Recover all the discarded tweets from the domain.
     *
     * @return Paged list of discarded tweets
     */
    Page<Tweet> listDiscardedTweets(Pageable page);

}