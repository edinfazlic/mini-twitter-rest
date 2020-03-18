package com.minitwitter.repository;

import com.minitwitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findOneByUsername(String user);
}
