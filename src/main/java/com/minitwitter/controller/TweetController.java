package com.minitwitter.controller;

import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.domain.dto.TweetDTO;
import com.minitwitter.service.TweetService;
import com.minitwitter.service.TweetService.InvalidTweetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("tweet")
@Slf4j
public class TweetController extends ExceptionHandlingController {

  private TweetService tweetService;

  public TweetController(TweetService tweetService) {
    this.tweetService = tweetService;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public TweetDTO tweet(@RequestBody String tweet, Principal principal) {
    return tweetService.createTweet(tweet, principal);
  }

  @GetMapping
  public Collection<TweetDTO> followingUsersTweets(Principal principal) {
    return tweetService.followingUsersTweets(principal);
  }

  @GetMapping(value = "{username}")
  public Collection<TweetDTO> tweetsFromUser(@PathVariable String username) {
    return tweetService.tweetsFromUser(username);
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  public ErrorMessage handleInvalidTweetException(InvalidTweetException e) {
    log.warn("", e);
    return new ErrorMessage("We're unable to accept tweet");
  }
}
