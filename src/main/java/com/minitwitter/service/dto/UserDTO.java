package com.minitwitter.service.dto;

import com.minitwitter.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class UserDTO {
  private Long id;
  private String username;

  public UserDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
