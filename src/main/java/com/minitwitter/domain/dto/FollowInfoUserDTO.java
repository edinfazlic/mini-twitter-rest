package com.minitwitter.domain.dto;

import com.minitwitter.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowInfoUserDTO extends UserDTO {
  boolean follower;
  boolean following;

  public FollowInfoUserDTO(User user) {
    super(user);
  }
}
