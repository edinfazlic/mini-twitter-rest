package com.minitwitter.service;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.UserOverviewDTO;
import com.minitwitter.repository.TweetRepository;
import com.minitwitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OverviewService extends ExceptionHandlingService {

  private TweetRepository tweetRepository;

  public OverviewService(TweetRepository tweetRepository, UserRepository userRepository) {
    super(userRepository);
    this.tweetRepository = tweetRepository;
  }

  @Transactional
  public UserOverviewDTO getUserOverview(String username) {
    int tweets = this.tweetRepository.countByAuthorUsername(username);

    User user = this.getUser(username);
    int followers = user.getFollowers().size();
    int following = user.getFollowing().size();

    return new UserOverviewDTO(tweets, followers, following);
  }

}
