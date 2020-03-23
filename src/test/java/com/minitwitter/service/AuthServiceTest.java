package com.minitwitter.service;

import com.minitwitter.IntegrationTest;
import com.minitwitter.domain.dto.AuthUserDTO;
import com.minitwitter.service.AuthService.UsernameExistsException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest extends IntegrationTest {

  @Autowired
  private AuthService authService;

  @Test(expected = UsernameExistsException.class)
  public void loadingUserWithUnknownUsername_UsernameExistsExceptionThrown() {
    AuthUserDTO authUser = new AuthUserDTO();
    authUser.setUsername(usernameOfAuthUser());
    authService.registerUser(authUser);
  }
}
