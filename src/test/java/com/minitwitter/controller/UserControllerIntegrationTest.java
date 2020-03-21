package com.minitwitter.controller;

import com.minitwitter.domain.dto.FollowInfoUserDTO;
import com.minitwitter.domain.dto.UserDTO;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserControllerIntegrationTest extends RestIntegrationTest {

  @Test
  public void followersRequested_allFollowersReturned() {
    ResponseEntity<UserDTO[]> response = withAuthTestRestTemplate().getForEntity("/user/followers", UserDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<UserDTO> followers = Arrays.asList(response.getBody());
    assertThat(followers, hasSize(followers().length));
    assertThat(extractUsernames(followers), containsInAnyOrder(followers()));
  }

  @Test
  public void getFollowingFromFirstPage() {
    ResponseEntity<UserDTO[]> response = withAuthTestRestTemplate().getForEntity("/user/following", UserDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<UserDTO> following = Arrays.asList(response.getBody());
    assertThat(following, hasSize(followingUsers().length));
    assertThat(extractUsernames(following), containsInAnyOrder(followingUsers()));
  }

  @Test
  public void searchRequested_allInfoAboutUsersReturned() {
    ResponseEntity<FollowInfoUserDTO[]> response = withAuthTestRestTemplate().getForEntity("/user?searchString=" + searchString(), FollowInfoUserDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<FollowInfoUserDTO> users = Arrays.asList(response.getBody());
    assertThat(users, hasSize(searchedUsersFound().length));
    assertThat(users.stream().map(FollowInfoUserDTO::getUsername).collect(toList()), containsInAnyOrder(searchedUsersFound()));
    assertThat(users.stream().filter(FollowInfoUserDTO::isFollower).count(), equalTo(1L));
    assertThat(users.stream().filter(followInfoUserDTO -> !followInfoUserDTO.isFollower()).count(), equalTo(1L));
    assertThat(users.stream().filter(FollowInfoUserDTO::isFollowing).count(), equalTo(2L));
    assertThat(users.stream().filter(followInfoUserDTO -> !followInfoUserDTO.isFollowing()).count(), equalTo(0L));
  }


  private List<String> extractUsernames(List<UserDTO> users) {
    return users.stream().map(UserDTO::getUsername).collect(toList());
  }
}
