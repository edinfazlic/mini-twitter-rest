package com.minitwitter;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class IntegrationTest {

  private static final String AUTH_USERNAME = "aantonop";
  private static final String AUTH_PASSWORD = "password";
  private static final String UNKNOWN_USERNAME = "unknown";

  protected String usernameOfAuthUser() {
    return AUTH_USERNAME;
  }

  protected String passwordOfAuthUser() {
    return AUTH_PASSWORD;
  }

  protected String unknownUsername() {
    return UNKNOWN_USERNAME;
  }
}
