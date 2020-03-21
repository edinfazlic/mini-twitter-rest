package com.minitwitter.repository;

import com.minitwitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
  User findOneByUsername(String user);

  /**
   * Use instead:
   * @see UserRepository#findByUsernameFirstNameLastName
   */
  Collection<User> findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String username, String firstName, String lastName);

  default Collection<User> findByUsernameFirstNameLastName(String searchString) {
    return findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchString, searchString, searchString);
  }

}
