package com.minitwitter.repository;

import com.minitwitter.domain.Tweet;
import com.minitwitter.domain.User;
import com.minitwitter.domain.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
  List<Tweet> findAllByAuthor(User author);
}
