package com.minitwitter.service;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.AuthUserDTO;
import com.minitwitter.domain.dto.UserDTO;
import com.minitwitter.repository.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

  private UserRepository userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserDTO registerUser(AuthUserDTO authUser) {
    User existingUser = getUser(authUser.getUsername());
    if (existingUser != null) {
      throw new UsernameExistsException(authUser.getUsername());
    }
    User user = new User(authUser.getUsername(), authUser.getPassword(), authUser.getFirstName(), authUser.getLastName());
    user = userRepository.save(user);
    return new UserDTO(user);
  }

  private User getUser(String username) {
    return userRepository.findOneByUsername(username);
  }

  public static class UsernameExistsException extends RuntimeException {
    @Getter
    private String username;

    private UsernameExistsException(String username) {
      super(username);
      this.username = username;
    }
  }
}
