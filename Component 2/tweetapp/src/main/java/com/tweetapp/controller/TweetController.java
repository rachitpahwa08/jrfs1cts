package com.tweetapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.dto.ReplyDTO;
import com.tweetapp.dto.TweetDTO;
import com.tweetapp.model.Tweet;
import com.tweetapp.service.TweetService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TweetController {

	private TweetService tweetService;
	
	@PostMapping("/{username}/add")
	public Tweet postTweet(@Valid @RequestBody TweetDTO tweet,@PathVariable String username) {
		return tweetService.postTweet(tweet, username);
	}
	
	@GetMapping("/all")
	public List<Tweet> getAllTweets(){
		return tweetService.getAllTweets();
	}
	
	@GetMapping("/{username}")
	public List<Tweet> getTweetsByUsername(@PathVariable String username){
		return tweetService.getTweetByUsername(username);
	}
	
	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<Object> updateTweet(@PathVariable String username,@PathVariable String id,@RequestBody TweetDTO tweetDTO){
		Map<String, Object> responseBody = new HashMap<>();
		if(tweetService.updateTweetByID(username, id, tweetDTO)!=null)
			responseBody.put("success", true);
		else
			responseBody.put("success", false);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<Object> deleteTweet(@PathVariable String username,@PathVariable String id){
		Map<String, Object> responseBody = new HashMap<>();
		if(Boolean.TRUE.equals(tweetService.deleteTweet(username, id)))
			responseBody.put("success", true);
		else
			responseBody.put("success", false);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	@PostMapping("/{username}/reply/{id}")
	public ResponseEntity<Object> postReply(@PathVariable String username,@PathVariable String id,@Valid @RequestBody ReplyDTO replyDTO){
		Map<String, Object> responseBody = new HashMap<>();
		if(tweetService.postReply(id, username, replyDTO)!=null)
			responseBody.put("success", true);
		else
			responseBody.put("success", false);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
}
