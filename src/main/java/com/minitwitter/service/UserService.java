package com.minitwitter.service;

import com.minitwitter.domain.User;
import com.minitwitter.domain.dto.FollowInfoUserDTO;
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
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = getUser(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return user;
  }

  @Transactional
  public Collection<UserDTO> getUsersFollowing(Principal principal) {
    User user = getUser(principal.getName());
    return convertUsersToDTOs(user.getFollowing());
  }

  @Transactional
  public Collection<UserDTO> getUsersFollowers(Principal principal) {
    User user = getUser(principal.getName());
    return convertUsersToDTOs(user.getFollowers());
  }

  @Transactional
  public Collection<FollowInfoUserDTO> searchUsers(String searchString, Principal principal) {
    User user = getUser(principal.getName());
    Collection<User> allUsersFiltered = userRepository.findByUsernameFirstNameLastName(searchString);

    return allUsersFiltered.stream()
      .map(filteredUser -> {
        final FollowInfoUserDTO followInfoUserDTO = new FollowInfoUserDTO(filteredUser);
        followInfoUserDTO.setFollowing(user.getFollowing().stream().anyMatch(usr -> usr.getId().equals(filteredUser.getId())));
        followInfoUserDTO.setFollower(user.getFollowers().stream().anyMatch(usr -> usr.getId().equals(filteredUser.getId())));
        return followInfoUserDTO;
      }).collect(toList());
  }

  private User getUser(String username) {
    return userRepository.findOneByUsername(username);
  }

  private List<UserDTO> convertUsersToDTOs(Set<User> users) {
    return users.stream().map(UserDTO::new).collect(toList());
  }
}
