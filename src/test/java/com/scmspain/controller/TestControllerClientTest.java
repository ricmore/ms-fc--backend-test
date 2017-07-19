package com.scmspain.controller;

import com.scmspain.configuration.TestConfiguration;
import com.scmspain.domain.entities.Tweet;
import com.scmspain.services.TweetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by ricardmore on 14/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestConfiguration.class})
@EnableSpringDataWebSupport
//@WebMvcTest(controllers = {TweetController.class})
//@Import(com.scmspain.configuration.TestConfiguration.class)
public class TestControllerClientTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Random server port just in case we needed it
     */
    @Value("${local.server.port}")
    int port;

    @MockBean
    private TweetService tweetService;
//
//    @MockBean
//    private TweetRepository tweetRepository;


    @InjectMocks
    private TweetController tweetController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(TestControllerClientTest.class);

        when(this.tweetService.listAllTweets(any(Pageable.class))).thenReturn(this.testPage());
    }

    @Test
    public void testFirstPage() throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity entity = new HttpEntity<>(header);

        ResponseEntity<List<TweetDTO>> listWrapper =
                this.restTemplate.exchange("/tweet", HttpMethod.GET, entity, new ParameterizedTypeReference<List<TweetDTO>>() {});

        assertThat(listWrapper.getStatusCode(), is(HttpStatus.OK));
        List<TweetDTO> tweets = listWrapper.getBody();
        assertThat(tweets.size(), is(1));
        TweetDTO first = tweets.get(0);
        assertThat(first.getPublisher(), is("Groucho Marx"));
    }

    private Page<Tweet> testPage() {
        return new PageImpl<Tweet>(Arrays.asList(this.testTweet()));
    }

    private Tweet testTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1l);
        tweet.setTweet("Those are my principles, and if you don't like them...well I have others.");
        tweet.setPublisher("Groucho Marx");

        return tweet;
    }

}
