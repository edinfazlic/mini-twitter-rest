package com.minitwitter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserOverviewDTO {
  private int tweets;
  private int totalFollowers;
  private int totalFollowing;
  private boolean follower;
  private boolean following;
}
