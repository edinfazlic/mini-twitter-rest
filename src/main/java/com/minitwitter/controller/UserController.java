package com.minitwitter.controller;

import com.minitwitter.domain.dto.UserDTO;
import com.minitwitter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("user")
public class UserController extends ExceptionHandlingController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/followers")
  public Collection<UserDTO> followers(Principal principal) {
    return userService.getUsersFollowers(principal);
  }

  @GetMapping("/following")
  public Collection<UserDTO> following(Principal principal) {
    return userService.getUsersFollowing(principal);
  }

  @GetMapping()
  public Collection<UserDTO> search(@RequestParam String searchString) {
    return userService.searchUsers(searchString);
  }

  @PostMapping("/follow/{username}")
  public void follow(@PathVariable String username, Principal principal) {
    userService.follow(username, principal);
  }

  @PostMapping("/unfollow/{username}")
  public void unfollow(@PathVariable String username, Principal principal) {
    userService.unfollow(username, principal);
  }
}
