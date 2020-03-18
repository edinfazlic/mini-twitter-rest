package com.minitwitter.repository;

import com.minitwitter.domain.Tweet;
import com.minitwitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
  List<Tweet> findAllByAuthor(User author);
}
