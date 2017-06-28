package com.scmspain.controller;

import com.scmspain.controller.command.DiscardTweetCommand;
import com.scmspain.controller.command.PublishTweetCommand;
import com.scmspain.entities.DiscardedTweet;
import com.scmspain.entities.Tweet;
import com.scmspain.services.DiscardTweetService;
import com.scmspain.services.DiscardedTweetService;
import com.scmspain.services.TweetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class TweetController {
    private TweetService tweetService;
    private DiscardTweetService discardTweetService;
    private DiscardedTweetService discardedTweetService;

    public TweetController(TweetService tweetService, DiscardTweetService discardTweetService, DiscardedTweetService discardedTweetService) {
        this.tweetService = tweetService;
        this.discardTweetService = discardTweetService;
        this.discardedTweetService = discardedTweetService;
    }

    @GetMapping("/tweet")
    public List<Tweet> listAllTweets() {
        return this.tweetService.listAllTweets();
    }

    @PostMapping("/tweet")
    @ResponseStatus(CREATED)
    public void publishTweet(@RequestBody PublishTweetCommand publishTweetCommand) {
        this.tweetService.publishTweet(publishTweetCommand.getPublisher(), publishTweetCommand.getTweet());
    }

    @GetMapping("/discarded")
    public List<DiscardedTweet> listDiscardedTweets() { return this.discardedTweetService.listAllTweets(); }

    @PostMapping("/discarded")
    @ResponseStatus(CREATED)
    public void discardTweet(@RequestBody DiscardTweetCommand discardTweetCommand ) {
        this.discardTweetService.discardTweet(discardTweetCommand.getId());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }
}
