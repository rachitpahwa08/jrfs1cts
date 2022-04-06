package com.tweetapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tweetapp.dto.ReplyDTO;
import com.tweetapp.dto.TweetDTO;
import com.tweetapp.exception.TweetAppException;
import com.tweetapp.mapper.TweetAppMapper;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.TweetRepositry;
import com.tweetapp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetService {

	private TweetRepositry tweetRepositry;
	
	private UserRepository userRepository;
	
	private TweetAppMapper tweetAppMapper;
	
	public Tweet postTweet(TweetDTO tweetDTO,String username) {
		User user = userRepository.findByLoginID(username);
		if(user == null)
			throw new TweetAppException("User with this username does not exists", HttpStatus.BAD_REQUEST.toString());
		Tweet tweet =tweetAppMapper.tweetDTOtoTweet(tweetDTO);
		tweet.setUserName(user.getLoginID());
		tweet.setPostedTime(LocalDateTime.now());
		tweet=tweetRepositry.save(tweet);
		if(user.getTweets()==null) {
			user.setTweets(new ArrayList<>());
		}
		user.getTweets().add(tweet);
		userRepository.save(user);
		return tweet;
	}
	
	public List<Tweet> getAllTweets() {
		return tweetRepositry.findAllByOrderByPostedTimeDesc();
	}
	
	public List<Tweet> getTweetByUsername(String userName){
		return tweetRepositry.findByUserName(userName);
	}
	
	public Tweet updateTweetByID(String username,String tweetID,TweetDTO updatedTweet) {
		User user = userRepository.findByLoginID(username);
		Tweet tweet=null;
		if(user == null)
			throw new TweetAppException("User with this username does not exists", HttpStatus.BAD_REQUEST.toString());
		Optional<Tweet> tweetOp = tweetRepositry.findById(tweetID);
		if(tweetOp.isPresent())
			tweet=tweetOp.get();
		else
			throw new TweetAppException("Invalid Tweet ID", HttpStatus.BAD_REQUEST.toString());
		if(!tweet.getUserName().equals(username))
			throw new TweetAppException("Tweet not found", HttpStatus.BAD_REQUEST.toString());
		tweet.setContent(updatedTweet.getContent());
		return tweetRepositry.save(tweet);
	}
	
	public Boolean deleteTweet(String username,String tweetID) {
		User user = userRepository.findByLoginID(username);
		Tweet tweet=null;
		if(user == null)
			throw new TweetAppException("User with this username does not exists", HttpStatus.BAD_REQUEST.toString());
		Optional<Tweet> tweetOp = tweetRepositry.findById(tweetID);
		if(tweetOp.isPresent())
			tweet=tweetOp.get();
		else
			throw new TweetAppException("Invalid Tweet ID", HttpStatus.BAD_REQUEST.toString());
		if(!tweet.getUserName().equals(username))
			throw new TweetAppException("Tweet not found", HttpStatus.BAD_REQUEST.toString());
		tweetRepositry.delete(tweet);
		return true;
	}
	
	public Tweet postReply(String tweetId,String username,ReplyDTO replyDTO) {
		User user = userRepository.findByLoginID(username);
		Tweet tweet = null;
		if(user == null)
			throw new TweetAppException("User with this username does not exists", HttpStatus.BAD_REQUEST.toString());
		Optional<Tweet> tweetOp = tweetRepositry.findById(tweetId);
		if(tweetOp.isPresent())
			tweet=tweetOp.get();
		else
			throw new TweetAppException("Invalid Tweet ID", HttpStatus.BAD_REQUEST.toString());
		if(tweet.getReplies()==null)
			tweet.setReplies(new ArrayList<>());
		tweet.getReplies().add(tweetAppMapper.replyDTOToReply(replyDTO, new ObjectId().toString(), username));
		tweetRepositry.save(tweet);
		return tweet;
	}	
}
