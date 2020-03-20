package com.minitwitter.controller;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.AuthUserDTO;
import com.minitwitter.domain.dto.UserDTO;
import com.minitwitter.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AuthControllerIntegrationTest extends RestIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void userSignup_userCreatedAndReturned() {
    ResponseEntity<UserDTO> response = doRegisterUser("mJordan");
    assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    UserDTO user = response.getBody();
    User fromDb = userRepository.findOne(user.getId());
    assertThat(fromDb, notNullValue());
    assertThat(fromDb.getUsername(), CoreMatchers.equalTo(user.getUsername()));
    assertThat(fromDb.getFirstName(), CoreMatchers.equalTo(user.getFirstName()));
    assertThat(fromDb.getLastName(), CoreMatchers.equalTo(user.getLastName()));
  }

  @Test
  public void userSignup_usernameAlreadyExists() {
    ResponseEntity<UserDTO> response = doRegisterUser(getUsernameOfAuthUser());
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
  }

  private ResponseEntity<UserDTO> doRegisterUser(String username) {
    AuthUserDTO authUser = new AuthUserDTO();
    authUser.setFirstName("michael");
    authUser.setLastName("jordan");
    authUser.setUsername(username);
    authUser.setPassword("airMike");
    return withoutAuthTestRestTemplate().postForEntity("/signup", authUser, UserDTO.class);
  }

}
