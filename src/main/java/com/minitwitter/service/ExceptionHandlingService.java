package com.minitwitter.service;

import com.minitwitter.domain.User;
import com.minitwitter.repository.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public abstract class ExceptionHandlingService {

  private UserRepository userRepository;

  protected ExceptionHandlingService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  protected User getUser(Principal principal) {
    return userRepository.findOneByUsername(principal.getName());
  }

  protected User getUser(String username) throws UnknownUsernameException {
    User user = userRepository.findOneByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UnknownUsernameException(username);
  }

  public static class UnknownUsernameException extends RuntimeException {
    @Getter
    private String username;

    private UnknownUsernameException(String username) {
      super(username);
      this.username = username;
    }
  }
}
