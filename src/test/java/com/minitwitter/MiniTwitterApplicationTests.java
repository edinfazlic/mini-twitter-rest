package com.minitwitter;

import com.minitwitter.domain.User;
import com.minitwitter.repository.TweetRepository;
import com.minitwitter.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiniTwitterApplicationTests {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TweetRepository tweetRepository;

  @Test
  @Transactional
  public void bootstrapDataIsLoaded_followersAreConnected() {
    User aantrop = userRepository.findOneByUsername("aantonop");
    assertThat(aantrop.getFollowers(), hasSize(1));
    assertThat(aantrop.getFollowing(), hasSize(4));
    assertThat(tweetRepository.count(), equalTo(8L));
  }
}
