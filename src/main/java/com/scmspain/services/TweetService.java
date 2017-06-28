package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.repository.TweetRepository;
import com.scmspain.validators.Validator;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TweetService {
    private TweetRepository repository;
    private MetricWriter metricWriter;
    private Validator<Tweet> validator;

    public TweetService(TweetRepository repository, MetricWriter metricWriter, Validator<Tweet> validator) {
        this.repository = repository;
        this.metricWriter = metricWriter;
        this.validator = validator;
    }

    /**
      Push tweet to repository
      Parameter - publisher - creator of the Tweet
      Parameter - text - Content of the Tweet
      Result - recovered Tweet
    */
    public void publishTweet(String publisher, String text) throws IllegalArgumentException {
        Tweet tweet = new Tweet();
        tweet.setTweet(text);
        tweet.setPublisher(publisher);

        validateTweet(tweet);

        this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
        this.repository.create(tweet);
    }

    /**
      Recover tweet from repository
      Parameter - id - id of the Tweet to retrieve
      Result - retrieved Tweet
    */
    public Tweet getTweet(Long id) {
      return this.repository.get(id);
    }

    /**
      Recover tweet from repository
      Parameter - id - id of the Tweet to retrieve
      Result - retrieved Tweet
    */
    public List<Tweet> listAllTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        return this.repository.getAll();
    }

    private void validateTweet(Tweet tweet) throws IllegalArgumentException {
        this.validator.validate(tweet);
    }
}
