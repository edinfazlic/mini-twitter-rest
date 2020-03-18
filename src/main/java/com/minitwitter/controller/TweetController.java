package com.minitwitter.controller;

import com.minitwitter.controller.dto.ErrorMessage;
import com.minitwitter.service.TweetService;
import com.minitwitter.service.TweetService.InvalidTweetException;
import com.minitwitter.service.TweetService.UnknownUsernameException;
import com.minitwitter.service.dto.TweetDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("tweets")
@Slf4j
public class TweetController {

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
  public ErrorMessage handleUnknownUsernameException(UnknownUsernameException e){
    log.warn("", e);
    return new ErrorMessage(String.format("Unknown user '%s'", e.getUsername()));
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  public ErrorMessage handleInvalidTweetException(InvalidTweetException e){
    log.warn("", e);
    return new ErrorMessage("We're unable to accept tweet");
  }
}
