package com.scmspain.services;

import com.scmspain.domain.entities.Tweet;
import com.scmspain.domain.repository.TweetRepository;
import com.scmspain.services.digester.TweetDigester;
import com.scmspain.services.impl.TweetServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Plain mockito test. Here we avoid Spring context so the test can run much faster.
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {

    @Mock
    private TweetRepository repository;

    @Mock
    private TweetDigester digester;

    @Mock
    private MetricWriter metricWriter;

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(TweetServiceTest.class);
        doNothing().when(this.digester).digest(any(Tweet.class));
        doNothing().when(this.digester).compose(any(Tweet.class));
        doNothing().when(this.metricWriter).increment(any(Delta.class));

        when(this.repository.findOne(this.testTweet().getId())).thenReturn(this.testTweet());
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        this.tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");

        verify(repository).save(any(Tweet.class));
    }

    @Test
    public void shouldDiscardATweet() throws Exception {
        this.tweetService.discardTweet(this.testTweet().getId());

        verify(repository).save(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }

    private Tweet testTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1l);
        tweet.setTweet("I am Guybrush Threepwood, mighty pirate.");
        tweet.setPublisher("Guybrush Threepwood");

        return tweet;
    }

}
