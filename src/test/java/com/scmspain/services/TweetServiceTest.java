package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.validators.TweetMessageValidator;
import com.scmspain.validators.TweetPublisherValidator;
import com.scmspain.validators.TweetValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.EntityManager;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TweetServiceTest {
    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetService tweetService;
    private TweetValidator tweetValidator;

    @Before
    public void setUp() throws Exception {
        this.entityManager = mock(EntityManager.class);
        this.metricWriter = mock(MetricWriter.class);
        this.tweetValidator = new TweetValidator(Arrays.asList(new TweetPublisherValidator(), new TweetMessageValidator()));


        this.tweetService = new TweetService(entityManager, metricWriter, tweetValidator);
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");

        verify(entityManager).persist(any(Tweet.class));
    }

    @Test
    public void shouldInsertANewTweetWithouthCountingLinkLength() throws Exception {
        tweetService.publishTweet("Classy Dude", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque interdum rutrum sodales. Nullam mattis fermentum libero, non volutpat http://foogle.co");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetIsNull() throws Exception {
        tweetService.publishTweet("Pirate", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetIsEmpty() throws Exception {
        tweetService.publishTweet("Pirate", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenPublisherIsNull() throws Exception {
        tweetService.publishTweet(null, "Null publisher test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenPublisherIsEmpty() throws Exception {
        tweetService.publishTweet("", "Empty publisher test");
    }
}
