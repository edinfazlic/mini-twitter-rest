package com.minitwitter.domain.dto;

import com.minitwitter.domain.Tweet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TweetDTO {
  private Long id;
  private String content;
  private UserDTO author;

  public TweetDTO(Tweet tweet) {
    this.id = tweet.getId();
    this.content = tweet.getContent();
    this.author = new UserDTO(tweet.getAuthor());
  }
}
