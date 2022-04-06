package com.tweetapp.utils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tweetapp.dto.UserDTO;
import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.User;
import com.tweetapp.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class HelperUtils {

	private UserRepository userRepository;
	
	public void validateUserDTO(UserDTO userDTO) {
		List<User> users=userRepository.findByEmailOrLoginID(userDTO.getEmail(), userDTO.getLoginID());
		log.info("user Value {}",users);
		if(!users.isEmpty()) {
			throw new TweetAppException("Email or LoginID already in use", HttpStatus.BAD_REQUEST.toString());
		}
		if(!userDTO.getPassword().equals(userDTO.getConfirmPassword()))
		{
			throw new TweetAppException("Password and Confirm Password does not match.", HttpStatus.BAD_REQUEST.toString());
		}
	}
}
