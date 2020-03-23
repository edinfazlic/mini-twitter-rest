package com.minitwitter.controller;

import com.minitwitter.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class RestIntegrationTest extends IntegrationTest {

  public static final String FOLLOW_UNFOLLOW_USER = "satoshiNakamoto";

  @Autowired
  private TestRestTemplate testRestTemplate;

  TestRestTemplate withAuthTestRestTemplate() {
    return testRestTemplate.withBasicAuth(usernameOfAuthUser(), passwordOfAuthUser());
  }

  TestRestTemplate withoutAuthTestRestTemplate() {
    return testRestTemplate;
  }

  protected String followUnfollowUser() {
    return FOLLOW_UNFOLLOW_USER;
  }
}
