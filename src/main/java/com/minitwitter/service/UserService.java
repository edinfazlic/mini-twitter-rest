package com.minitwitter.service;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.UserDTO;
import com.minitwitter.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserService extends ExceptionHandlingService implements UserDetailsService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUser(username);
  }

  @Transactional
  public Collection<UserDTO> getUsersFollowing(Principal principal) {
    User user = getUser(principal);
    return convertUsersToDTOs(user.getFollowing());
  }

  @Transactional
  public Collection<UserDTO> getUsersFollowers(Principal principal) {
    User user = getUser(principal);
    return convertUsersToDTOs(user.getFollowers());
  }

  @Transactional
  public Collection<UserDTO> searchUsers(String searchString, Principal principal) {
    String loggedUser = principal.getName();
    Collection<User> allUsersFiltered = userRepository.findByUsernameFirstNameLastName(searchString);
    allUsersFiltered = allUsersFiltered.stream().filter(user -> !loggedUser.equals(user.getUsername())).collect(Collectors.toList());
    return convertUsersToDTOs(allUsersFiltered);
  }

  @Transactional
  public void follow(String username, Principal principal) {
    User user = getUser(principal);
    User followedUser = getUser(username);
    user.addFollowing(followedUser);
    userRepository.save(user);
  }

  @Transactional
  public void unfollow(String username, Principal principal) {
    User user = getUser(principal);
    User followedUser = getUser(username);
    user.removeFollowing(followedUser);
    userRepository.save(user);
  }

  private List<UserDTO> convertUsersToDTOs(Collection<User> users) {
    return users.stream().map(UserDTO::new).collect(toList());
  }
}
