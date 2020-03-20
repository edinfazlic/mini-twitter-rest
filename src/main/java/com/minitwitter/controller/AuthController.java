package com.minitwitter.controller;

import com.minitwitter.domain.dto.AuthUserDTO;
import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.domain.dto.UserDTO;
import com.minitwitter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Slf4j
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  @ResponseStatus(CREATED)
  public UserDTO registerUser(@RequestBody AuthUserDTO user) {
    return authService.registerUser(user);
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  public ErrorMessage handleUnknownUsernameException(AuthService.UsernameExistsException e) {
    log.warn("", e);
    return new ErrorMessage(String.format("Username exists '%s'", e.getUsername()));
  }
}
