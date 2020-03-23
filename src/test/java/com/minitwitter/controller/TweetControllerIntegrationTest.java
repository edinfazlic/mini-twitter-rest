package com.minitwitter.controller;

import com.minitwitter.domain.Tweet;
import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.ErrorMessage;
import com.minitwitter.domain.dto.TweetDTO;
import com.minitwitter.repository.TweetRepository;
import com.minitwitter.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TweetControllerIntegrationTest extends RestIntegrationTest {

  @Autowired
  private TweetRepository tweetRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void tooLongTweetCreated_BadRequestReceived() {
    String request = IntStream.rangeClosed(1, 141).mapToObj(String::valueOf).collect(joining());
    ResponseEntity<TweetDTO> response = doCreateTweetRequest(request);
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
  }

  @Test
  public void createTweet_tweetIsSaved() {
    ResponseEntity<TweetDTO> response = doCreateTweetRequest("Tweet");
    assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    TweetDTO tweet = response.getBody();
    Tweet fromDb = tweetRepository.findOne(tweet.getId());
    assertThat(fromDb, notNullValue());
    assertThat(fromDb.getContent(), equalTo(tweet.getContent()));
    assertThat(fromDb.getAuthor().getUsername(), equalTo(tweet.getAuthor().getUsername()));
  }

  private ResponseEntity<TweetDTO> doCreateTweetRequest(String tweetContent) {
    return withAuthTestRestTemplate().postForEntity("/tweet", tweetContent, TweetDTO.class);
  }

  @Test
  @Transactional
  public void tweetsFromFollowingRequested_onlyFollowingAuthorsTweetsAreReturned() {
    ResponseEntity<TweetDTO[]> response = withAuthTestRestTemplate().getForEntity("/tweet", TweetDTO[].class);
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<String> authors = extractAuthorNames(response.getBody());
    User dbUser = userRepository.findOneByUsername(usernameOfAuthUser());
    assertThat(authors, hasItems(extractUsernames(dbUser.getFollowing())));
  }

  @Test
  public void tweetsFromUserRequested_onlyUserTweetsAreReturned() {
    final String requestedUser = usernameOfAuthUser();
    ResponseEntity<TweetDTO[]> response = withAuthTestRestTemplate().getForEntity("/tweet/{username}",
      TweetDTO[].class, Collections.singletonMap("username", requestedUser));
    assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
    List<String> authors = extractAuthorNames(response.getBody());
    assertThat(authors, contains(requestedUser));
  }

  @Test
  public void tweetsFromUserWithWrongUsername_badRequestReturned() {
    final String requestedUser = unknownUsername();
    ResponseEntity<ErrorMessage> response = withAuthTestRestTemplate().getForEntity("/tweet/{username}",
      ErrorMessage.class, Collections.singletonMap("username", requestedUser));
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(response.getBody().getMessage(), containsString(requestedUser));
  }

  private String[] extractUsernames(Collection<User> users) {
    return users.stream().map(User::getUsername).toArray(String[]::new);
  }


  private List<String> extractAuthorNames(TweetDTO[] body) {
    return Arrays.stream(body).map(tweetDTO -> tweetDTO.getAuthor().getUsername()).distinct().collect(toList());
  }
}
