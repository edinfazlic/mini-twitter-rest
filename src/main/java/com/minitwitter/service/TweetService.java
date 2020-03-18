package com.minitwitter.service;

import com.minitwitter.domain.Tweet;
import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.TweetDTO;
import com.minitwitter.repository.TweetRepository;
import com.minitwitter.repository.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TweetService {

  private TweetRepository tweetRepository;
  private UserRepository userRepository;

  public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
    this.tweetRepository = tweetRepository;
    this.userRepository = userRepository;
  }

  public Collection<TweetDTO> followingUsersTweets(Principal principal) {
    User author = getUser(principal);
    return author.getFollowing().stream().map(this::tweetsFromUser).flatMap(Collection::stream).collect(toList());
  }

  public Collection<TweetDTO> tweetsFromUser(String username) {
    User user = getUser(username);
    return tweetsFromUser(user);
  }

  public TweetDTO createTweet(String tweetContent, Principal principal) {
    User user = getUser(principal);
    Tweet tweet = new Tweet(tweetContent, user);
    if (!tweet.isValid()) {
      throw new InvalidTweetException(tweetContent);
    }
    Tweet saved = tweetRepository.save(tweet);
    return new TweetDTO(saved);
  }

  private User getUser(Principal principal) {
    return getUser(principal.getName());
  }

  private List<TweetDTO> tweetsFromUser(User user) {
    return tweetRepository.findAllByAuthor(user).stream().map(TweetDTO::new).collect(toList());
  }

  private User getUser(String username) {
    User user = userRepository.findOneByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UnknownUsernameException(username);
  }

  public static class UnknownUsernameException extends RuntimeException {
    @Getter
    private String username;

    private UnknownUsernameException(String username) {
      super(username);
      this.username = username;
    }
  }

  public static class InvalidTweetException extends RuntimeException {

    private InvalidTweetException(String tweet) {
      super("'" + tweet + "'");
    }
  }
}