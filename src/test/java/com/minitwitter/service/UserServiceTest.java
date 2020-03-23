package com.minitwitter.service;

import com.minitwitter.IntegrationTest;
import com.minitwitter.service.ExceptionHandlingService.UnknownUsernameException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest extends IntegrationTest {

  @Autowired
  private UserService userService;

  @Test(expected = UnknownUsernameException.class)
  public void loadingUserWithUnknownUsername_UnknownUsernameExceptionThrown() {
    userService.loadUserByUsername(unknownUsername());
  }
}
