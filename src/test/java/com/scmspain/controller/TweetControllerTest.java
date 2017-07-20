package com.scmspain.controller;

import com.scmspain.configuration.TestConfiguration;
import com.scmspain.domain.entities.Tweet;
import com.scmspain.services.TweetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
@EnableSpringDataWebSupport
// This annotation makes the test perform so bad... I know it sorry. I could have mocked service layer instead.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TweetService tweetService;

//    @Before
//    public void setUp() {
//        this.mockMvc = webAppContextSetup(this.context).build();
//    }

    /**
     * We simply test a new tweet is created.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnCreatedWhenInsertingAValidTweet() throws Exception {
        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
            .andExpect(status().isCreated());
    }

    /**
     * We try to add a tweet with a text longer than 140 chars.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnBadRequestWhenInsertingAnInvalidTweet() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain, we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome! We are Schibsted Spain, we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().isBadRequest());
    }

    /**
     * We are adding a tweet with a text containing 2 links. The original text is longer that 140, but after extracting the links it becomes shorter.
     *
     * @throws Exception
     */
    @Test
    public void testTweetWithLinks() throws Exception {
        // Text is longer than 140, but once the links are removed it becomes shorter...

        // Developer (Ricard Moré) comment.
        // Why was this http://www.schibsted.es/ link originally followed by ), ???
        // According to the specs: A link is any set of non-whitespace consecutive characters starting with http:// or https:// and finishing with a space.
        // So according to such specs originally the link was http://www.schibsted.es/),
        // I've added a space... dunno if this was made on purpose or just a mistake.
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/ ), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome! http://www.schibsted.es/"))
                .andExpect(status().isCreated());
    }

    /**
     * Here we test two tweets are published. First we call /tweet twice and then we recover all the tweets straight from service layer.
     * The list should be sorted by publication date.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnAllPublishedTweets() throws Exception {
        mockMvc.perform(newTweet("Yo", "How are you?"))
                .andExpect(status().isCreated());

        // Sleep for 1 sec to be sure when we recover the list will be sorted by publication date desc
        TimeUnit.SECONDS.sleep(1);

        mockMvc.perform(newTweet("Yo", "This is the link http://www.one.es and the second one http://www.second.es "))
                .andExpect(status().isCreated());

        Page<Tweet> tweetsPage = this.tweetService.listAllTweets(new PageRequest(0, 10));
        assertThat(tweetsPage.getContent().size(), is(2));

        // First published tweet will be the last in the list
        Tweet tweet = tweetsPage.getContent().get(1);

        assertThat(tweet.getTweet(), is("How are you?"));
        assertThat(tweet.getDiscarded().booleanValue(), is(false));

        Tweet tweet2 = tweetsPage.getContent().get(0);
        assertThat(tweet2.getTweet(), is("This is the link http://www.one.es and the second one http://www.second.es "));

    }

    /**
     * First we create a tweet and then we discard it
     *
     * @throws Exception
     */
    @Test
    public void discardTweet() throws Exception {
        mockMvc.perform(newTweet("Yo", "How are you?"))
                .andExpect(status().isCreated());

        // As the database was empty we assume the id for the only tweet is 1.
        mockMvc.perform(discardTweet(1l))
                .andExpect(status().isOk());
    }

    /**
     * Trying to discard an unexistent tweet.
     *
     * @throws Exception
     */
    @Test
    public void discardInvalidTweet() throws Exception {
        mockMvc.perform(discardTweet(10l))
                .andExpect(status().isBadRequest());
    }

    /**
     * We publish two tweets, and then we discard the 1st one. The we validate that the list of published tweets contains only one element, same size as
     * discarded tweets list.
     *
     * @throws Exception
     */
    @Test
    public void discard1Tweet() throws Exception {
        mockMvc.perform(newTweet("Yo", "Discarded"))
                .andExpect(status().isCreated());

        mockMvc.perform(newTweet("Yo", "OK"))
                .andExpect(status().isCreated());

        mockMvc.perform(discardTweet(1l))
                .andExpect(status().isOk());

        Page<Tweet> tweetsOk = this.tweetService.listAllTweets(new PageRequest(0, 10));
        assertThat(tweetsOk.getContent().size(), is(1));

        assertThat(tweetsOk.getContent().get(0).getTweet(), is("OK"));

        Page<Tweet> tweetsDiscarded = this.tweetService.listDiscardedTweets(new PageRequest(0, 10));
        assertThat(tweetsDiscarded.getContent().size(), is(1));

        assertThat(tweetsDiscarded.getContent().get(0).getTweet(), is("Discarded"));
    }

    private MockHttpServletRequestBuilder newTweet(String publisher, String tweet) {
        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
    }

    private MockHttpServletRequestBuilder discardTweet(Long tweetId) {
        return post("/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"tweet\": \"%d\"}", tweetId));
    }

}
