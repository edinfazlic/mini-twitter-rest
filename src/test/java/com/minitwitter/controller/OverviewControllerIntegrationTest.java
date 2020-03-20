package com.minitwitter.controller;

import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.domain.dto.UserOverviewDTO;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OverviewControllerIntegrationTest extends RestIntegrationTest {

  @Test
  public void overviewOfUser_onlyThatUserTweetsAreReturned() {
    ResponseEntity<UserOverviewDTO> response = withAuthTestRestTemplate().getForEntity("/overview/{username}",
      UserOverviewDTO.class, Collections.singletonMap("username", getUsernameOfAuthUser()));
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    UserOverviewDTO overview = response.getBody();
    assertThat(overview.getTweets(), equalTo(ownTweetsCount()));
    assertThat(overview.getFollowers(), equalTo(followersCount()));
    assertThat(overview.getFollowing(), equalTo(followingUsers().length));
  }

  @Test
  public void overviewOfUserWithWrongUsername_badRequestReturned() {
    ResponseEntity<ErrorMessage> response = withAuthTestRestTemplate().getForEntity("/overview/{username}",
      ErrorMessage.class, Collections.singletonMap("username", "unknown"));
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(response.getBody().getMessage(), containsString("unknown"));
  }

}
