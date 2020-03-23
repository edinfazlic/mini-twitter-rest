package com.minitwitter.service;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.UserOverviewDTO;
import com.minitwitter.repository.TweetRepository;
import com.minitwitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class OverviewService extends ExceptionHandlingService {

  private TweetRepository tweetRepository;

  public OverviewService(TweetRepository tweetRepository, UserRepository userRepository) {
    super(userRepository);
    this.tweetRepository = tweetRepository;
  }

  @Transactional
  public UserOverviewDTO getUserOverview(String username, Principal principal) {
    int tweets = this.tweetRepository.countByAuthorUsername(username);

    User user = this.getUser(username);
    int totalFollowers = user.getFollowers().size();
    int totalFollowing = user.getFollowing().size();
    boolean isFollower;
    boolean isFollowing;
    if (principal.getName().equals(username)) {
      isFollower = false;
      isFollowing = false;
    } else {
      isFollower = user.getFollowing().stream().anyMatch(usr -> principal.getName().equals(usr.getUsername()));
      isFollowing = user.getFollowers().stream().anyMatch(usr -> principal.getName().equals(usr.getUsername()));
    }

    return new UserOverviewDTO(tweets, totalFollowers, totalFollowing, isFollower, isFollowing);
  }

}
