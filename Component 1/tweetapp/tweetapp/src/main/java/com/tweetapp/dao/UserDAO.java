package com.tweetapp.dao;

import com.tweetapp.models.Tweet;
import com.tweetapp.models.User;
import com.tweetapp.models.UserAndTweet;

import java.util.List;

public interface UserDAO {
     boolean register(User user);
     User login(String email,String password);
     boolean changePassword(int userId,String newPassword,String oldPassword);
     boolean postTweet(int userId,String tweetContent);
     List<Tweet> getUserTweets(int userId);
     List<UserAndTweet> getAllUserWithTweets();
}
