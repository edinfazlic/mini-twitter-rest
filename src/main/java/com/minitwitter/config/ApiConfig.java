package com.minitwitter.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ApiConfig {

  @Value("${CLIENT_ENVIRONMENT:http://localhost:4200}")
  private String clientEnvironment;

}
