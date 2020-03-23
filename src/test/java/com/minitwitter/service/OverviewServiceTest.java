package com.minitwitter.service;

import com.minitwitter.IntegrationTest;
import com.minitwitter.domain.dto.UserOverviewDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertFalse;

@SpringBootTest
public class OverviewServiceTest extends IntegrationTest {

  @Autowired
  private OverviewService overviewService;

  @Test(expected = ExceptionHandlingService.UnknownUsernameException.class)
  public void getOverviewWithUnknownUsername_UnknownUsernameExceptionThrown() {
    overviewService.getUserOverview(unknownUsername(), this::usernameOfAuthUser);
  }

  @Test
  public void getOverviewWithAuthUsername_followerFollowingReturnedFalse() {
    UserOverviewDTO overview = overviewService.getUserOverview(usernameOfAuthUser(), this::usernameOfAuthUser);
    assertFalse(overview.isFollower());
    assertFalse(overview.isFollowing());
  }
}
