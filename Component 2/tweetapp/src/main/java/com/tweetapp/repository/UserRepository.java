package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	List<User> findByEmailOrLoginID(String email,String loginID);
	
	User findByEmailAndPassword(String email,String password);
	
	User findByLoginID(String loginID);
	
	List<User> findByLoginIDLike(String loginID);
}
