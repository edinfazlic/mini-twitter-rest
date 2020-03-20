package com.minitwitter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserOverviewDTO {
  private int tweets;
  private int followers;
  private int following;
}
