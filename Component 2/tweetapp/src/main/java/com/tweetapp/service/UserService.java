package com.tweetapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tweetapp.dto.ForgotPasswordDTO;
import com.tweetapp.dto.UserDTO;
import com.tweetapp.exception.TweetAppException;
import com.tweetapp.mapper.TweetAppMapper;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.TweetRepositry;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.utils.HelperUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {


	private UserRepository userRepository;
	private TweetAppMapper tweetAppMapper;
	private TweetRepositry tweetRepositry;
	private HelperUtils helperUtils;
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	//@Transactional
	public UserDTO registerUser(UserDTO userDTO) {
		helperUtils.validateUserDTO(userDTO);
		User user=tweetAppMapper.userDTOtoUser(userDTO);
		user=userRepository.save(user);
//		List<Tweet> tweets = new ArrayList<>();
//		tweets.add(new Tweet("Test Tweet 4",user.getId()));
//		tweets=tweetRepositry.saveAll(tweets);
//		user.setTweets(tweets);
//		user=userRepository.save(user);
		userDTO.setId(user.getId());
		return userDTO;
	}
	public UserDTO loginUser(String email,String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if(user==null) {
			throw new TweetAppException("Incorrect Email/Password", HttpStatus.UNAUTHORIZED.toString());
		}
		return tweetAppMapper.userToUserDTO(user);
	}
	public User forgotPassword(String userName,ForgotPasswordDTO forgotPasswordDTO) {
		User user = userRepository.findByLoginID(userName);
		if(user == null)
			throw new TweetAppException("User with this username does not exists", HttpStatus.BAD_REQUEST.toString());
		if(!forgotPasswordDTO.getNewPassword().equals(forgotPasswordDTO.getConfirmPassword()))
			throw new TweetAppException("newPassword and confirmPassword do not match", HttpStatus.BAD_REQUEST.toString());
		user.setPassword(forgotPasswordDTO.getNewPassword());
		return userRepository.save(user);
	}
	
	public List<User> searchUsers(String username) {
		return userRepository.findByLoginIDLike(username);
	}
}
