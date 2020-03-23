package com.minitwitter.controller;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.domain.dto.UserDTO;
import com.minitwitter.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserControllerIntegrationTest extends RestIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional
  public void followersRequested_allFollowersReturned() {
    ResponseEntity<UserDTO[]> response = withAuthTestRestTemplate().getForEntity("/user/followers", UserDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<UserDTO> followers = Arrays.asList(response.getBody());
    User dbUser = userRepository.findOneByUsername(usernameOfAuthUser());
    assertThat(extractUsernames(followers), containsInAnyOrder(extractUsernames(dbUser.getFollowers())));
  }

  @Test
  @Transactional
  public void followingUsersRequested_allUsersFollowingReturned() {
    ResponseEntity<UserDTO[]> response = withAuthTestRestTemplate().getForEntity("/user/following", UserDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<UserDTO> following = Arrays.asList(response.getBody());
    User dbUser = userRepository.findOneByUsername(usernameOfAuthUser());
    assertThat(extractUsernames(following), containsInAnyOrder(extractUsernames(dbUser.getFollowing())));
  }

  @Test
  @Transactional
  public void searchRequested_searchResultReturned() {
    final String searchString = "er";
    ResponseEntity<UserDTO[]> response = withAuthTestRestTemplate().getForEntity("/user?searchString=" + searchString, UserDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<UserDTO> users = Arrays.asList(response.getBody());
    final List<String> usernames = extractUsernames(users);
    assertThat(usernameOfAuthUser(), not(isIn(usernames)));
    Collection<User> dbUsers = userRepository.findByUsernameFirstNameLastName(searchString);
    assertThat(usernames, containsInAnyOrder(extractUsernames(dbUsers)));
  }

  @Test
  public void unfollowUser_badRequestReturned() {
    ResponseEntity<ErrorMessage> response = withAuthTestRestTemplate().postForEntity("/user/unfollow/{username}",
      null, ErrorMessage.class, Collections.singletonMap("username", unknownUsername()));
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(response.getBody().getMessage(), containsString(unknownUsername()));
  }

  @Test
  public void followUser_badRequestReturned() {
    ResponseEntity<ErrorMessage> response = withAuthTestRestTemplate().postForEntity("/user/follow/{username}",
      null, ErrorMessage.class, Collections.singletonMap("username", unknownUsername()));
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(response.getBody().getMessage(), containsString(unknownUsername()));
  }

  @Test
  public void unfollowUser_successful() {
    ResponseEntity<Void> response = withAuthTestRestTemplate().postForEntity("/user/unfollow/{username}",
      null, Void.class, Collections.singletonMap("username", followUnfollowUser()));
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
  }

  @Test
  public void followUser_successful() {
    ResponseEntity<Void> response = withAuthTestRestTemplate().postForEntity("/user/follow/{username}",
      null, Void.class, Collections.singletonMap("username", followUnfollowUser()));
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
  }

  private String[] extractUsernames(Collection<User> users) {
    return users.stream().map(User::getUsername).toArray(String[]::new);
  }

  private List<String> extractUsernames(List<UserDTO> users) {
    return users.stream().map(UserDTO::getUsername).collect(toList());
  }
}
