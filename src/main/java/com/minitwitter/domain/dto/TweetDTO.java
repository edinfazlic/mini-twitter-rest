package com.minitwitter.domain.dto;

import com.minitwitter.domain.Tweet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class TweetDTO {
  private Long id;
  private String content;
  private String author;

  public TweetDTO(Tweet tweet) {
    this.id = tweet.getId();
    this.content = tweet.getContent();
    this.author = tweet.getAuthor().getUsername();
  }
}
