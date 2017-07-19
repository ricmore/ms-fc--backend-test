package com.scmspain.controller;

import com.scmspain.controller.command.PublishTweetCommand;
import com.scmspain.services.TweetService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "Tweet public API", produces = APPLICATION_JSON_UTF8_VALUE)
@RestController
public class TweetController {

    private TweetService tweetService;
    private ConversionService conversionService;

    @Autowired
    public TweetController(TweetService tweetService, ConversionService conversionService) {
        this.conversionService = conversionService;
        this.tweetService = tweetService;
    }

    @ApiOperation(value = "Reads a paged list of tweets sorted by publication date desc", httpMethod = "GET")
    @GetMapping(path = "/tweet", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    public List<TweetDTO> listAllTweets(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        return this.tweetService.listAllTweets(pageable).getContent().stream().map(t -> this.conversionService.convert(t, TweetDTO.class)).collect(Collectors.toList());
    }

    @ApiOperation(value = "Publishes and stotes a new tweet", httpMethod = "POST")
    @PostMapping(path = "/tweet", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public void publishTweet(@RequestBody PublishTweetCommand publishTweet) throws InvalidArgumentException {
        this.tweetService.publishTweet(publishTweet.getPublisher(), publishTweet.getTweet());
    }

    @ApiOperation(value = "Discard a tweet", httpMethod = "POST")
    @PostMapping(path = "/discarded", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void discardTweet(@RequestBody TweetId discardedTweet) {
        this.tweetService.discardTweet(discardedTweet.getTweet());
    }

    @ApiOperation(value = "Reads a paged list of discarded tweets sorted by discartion date desc", httpMethod = "GET")
    @GetMapping(path = "/discarded", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    public Page<TweetDTO> listDiscardedTweets(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        return this.tweetService.listDiscardedTweets(pageable).map(t -> this.conversionService.convert(t, TweetDTO.class));
    }


    // Exception management moved to RestExceptionHandler

//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(BAD_REQUEST)
//    @ResponseBody
//    public Object invalidArgumentException(IllegalArgumentException ex) {
//        return new Object() {
//            public String message = ex.getMessage();
//            public String exceptionClass = ex.getClass().getSimpleName();
//        };
//    }
}
