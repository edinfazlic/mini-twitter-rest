package com.minitwitter.domain.dto;

import com.minitwitter.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class UserDTO {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;

  public UserDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
  }
}
