package com.minitwitter.controller;

import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.service.ExceptionHandlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@Slf4j
public abstract class ExceptionHandlingController {

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  public ErrorMessage handleUnknownUsernameException(ExceptionHandlingService.UnknownUsernameException e) {
    log.warn("", e);
    return new ErrorMessage(String.format("Unknown user '%s'", e.getUsername()));
  }
}
