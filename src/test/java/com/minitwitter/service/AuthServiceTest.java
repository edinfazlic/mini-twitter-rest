package com.minitwitter.service;

import com.minitwitter.domain.dto.AuthUserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

  @Autowired
  private AuthService authService;

  @Test(expected = AuthService.UsernameExistsException.class)
  public void loadingUserWithUnknownUsername_UsernameNotFoundExceptionThrown() {
    AuthUserDTO authUser = new AuthUserDTO();
    authUser.setUsername("aantonop");
    authService.registerUser(authUser);
  }
}
