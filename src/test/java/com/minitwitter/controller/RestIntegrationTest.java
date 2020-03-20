package com.minitwitter.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class RestIntegrationTest {

  private static final String AUTH_USERNAME = "aantonop";
  private static final int OWN_TWEETS_COUNT = 3;
  private static final int FOLLOWERS_COUNT = 1;
  private static final String[] FOLLOWING_USERS = new String[]{"rogerkver", "satoshiNakamoto", "SatoshiLite", "VitalikButerin"};
  private static final String[] FOLLOWERS = new String[]{"rogerkver"};

  @Autowired
  private TestRestTemplate testRestTemplate;

  TestRestTemplate withAuthTestRestTemplate() {
    return testRestTemplate.withBasicAuth(AUTH_USERNAME, "password");
  }

  TestRestTemplate withoutAuthTestRestTemplate() {
    return testRestTemplate;
  }

  String getUsernameOfAuthUser() {
    return AUTH_USERNAME;
  }

  String[] followingUsers() {
    return FOLLOWING_USERS;
  }

  String[] followers() {
    return FOLLOWERS;
  }

  int ownTweetsCount() {
    return OWN_TWEETS_COUNT;
  }

  int followersCount() {
    return FOLLOWERS_COUNT;
  }
}
