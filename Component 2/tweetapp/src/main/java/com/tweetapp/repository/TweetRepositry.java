package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.Tweet;

@Repository
public interface TweetRepositry extends MongoRepository<Tweet, String>{

	List<Tweet> findAllByOrderByPostedTimeDesc();
	
	List<Tweet> findByUserName(String userName);
}

