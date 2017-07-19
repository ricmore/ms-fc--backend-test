package com.scmspain.services.impl;

import com.scmspain.domain.entities.Tweet;
import com.scmspain.domain.repository.TweetRepository;
import com.scmspain.services.TweetService;
import com.scmspain.services.digester.TweetDigester;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TweetServiceImpl implements TweetService {

    private TweetRepository repository;
    private MetricWriter metricWriter;
    private TweetDigester digester;

    @Autowired
    public TweetServiceImpl(TweetRepository repository, MetricWriter metricWriter, TweetDigester digester) {
        this.repository = repository;
        this.metricWriter = metricWriter;
        this.digester = digester;
    }

    @Override
    public Tweet publishTweet(String publisher, String text) throws InvalidArgumentException {
        Tweet tweet = new Tweet();
        tweet.setTweet(text);
        tweet.setPublisher(publisher);
        tweet.setPublicationDate(LocalDateTime.now());
        tweet.setDiscarded(false);

        this.digester.digest(tweet);

        if (tweet.getTweet().length() > Tweet.MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("Length of a tweet can't exceed %d characters", Tweet.MAX_LENGTH));
        }

        this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
        Tweet saved = this.repository.save(tweet);
        return saved;
    }

    @Override
    public Optional<Tweet> getTweet(@NotNull Long id) {
        Tweet tweet = this.repository.findOne(id);
        Objects.requireNonNull(tweet, "Unknown tweet id " + id);
        this.digester.compose(tweet);
        return Optional.ofNullable(tweet);
    }

    @Override
    public Page<Tweet> listAllTweets(Pageable page) {
        Page<Tweet> tweets = this.repository.findByDiscardedOrderByPublicationDateDesc(false, page);
        tweets.getContent().forEach(
                tweet -> {
                    this.digester.compose(tweet);
                });
        return tweets;
    }

    @Override
    public Tweet discardTweet(Long tweetId) {
        Tweet tweet = this.repository.findOne(tweetId);
        Objects.requireNonNull(tweet, "Unknown tweet id " + tweetId);
        tweet.setDiscarded(true);
        tweet.setDiscardedDate(LocalDateTime.now());

        return this.repository.save(tweet);
    }

    @Override
    public Page<Tweet> listDiscardedTweets(Pageable page) {
        Page<Tweet> tweets = this.repository.findByDiscardedOrderByDiscardedDateDesc(true, page);
        tweets.getContent().forEach(
                tweet -> {
                    this.digester.compose(tweet);
                });
        return tweets;
    }
}
