package com.minitwitter.controller;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.domain.dto.UserOverviewDTO;
import com.minitwitter.repository.TweetRepository;
import com.minitwitter.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OverviewControllerIntegrationTest extends RestIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TweetRepository tweetRepository;

  @Test
  @Transactional
  public void userOverviewRequested_thatUserOverviewReturned() {
    final String requestedUser = followUnfollowUser();
    ResponseEntity<UserOverviewDTO> response = withAuthTestRestTemplate().getForEntity("/overview/{username}",
      UserOverviewDTO.class, Collections.singletonMap("username", requestedUser));
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    UserOverviewDTO overview = response.getBody();
    User dbUser = userRepository.findOneByUsername(requestedUser);
    int dbTotalTweets = tweetRepository.countByAuthorUsername(requestedUser);
    assertThat(overview.getTweets(), equalTo(dbTotalTweets));
    assertThat(overview.getTotalFollowers(), equalTo(dbUser.getFollowers().size()));
    assertThat(overview.getTotalFollowing(), equalTo(dbUser.getFollowing().size()));
    assertThat(overview.isFollower(), is(dbUser.getFollowing().stream().anyMatch(user -> usernameOfAuthUser().equals(user.getUsername()))));
    assertThat(overview.isFollowing(), is(dbUser.getFollowers().stream().anyMatch(user -> usernameOfAuthUser().equals(user.getUsername()))));
  }

  @Test
  public void overviewOfUserWithWrongUsername_badRequestReturned() {
    ResponseEntity<ErrorMessage> response = withAuthTestRestTemplate().getForEntity("/overview/{username}",
      ErrorMessage.class, Collections.singletonMap("username", unknownUsername()));
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(response.getBody().getMessage(), containsString(unknownUsername()));
  }

}
