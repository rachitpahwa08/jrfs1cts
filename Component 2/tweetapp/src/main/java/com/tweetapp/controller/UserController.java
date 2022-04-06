package com.tweetapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.dto.ForgotPasswordDTO;
import com.tweetapp.dto.UserDTO;
import com.tweetapp.dto.UserLoginDTO;
import com.tweetapp.model.User;
import com.tweetapp.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	private UserService userService;
	
	
	@PostMapping("/register")
	public UserDTO registerUser(@Valid @RequestBody UserDTO userDTO) {
		return userService.registerUser(userDTO);
	}
	
	@PostMapping("/login")
	public UserDTO loginUser(@RequestBody UserLoginDTO userLoginDTO) {
		return userService.loginUser(userLoginDTO.getEmail(),userLoginDTO.getPassword());
	}
	
	@GetMapping("/users/all")
	public List<User> getAllUsers(){
		return userService.getUsers();
	}
	
	@PostMapping("/{username}/forgot")
	public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO,@PathVariable String username){
		Map<String,Object> responseBody= new HashMap<>();
		if(userService.forgotPassword(username, forgotPasswordDTO)!=null)
			responseBody.put("success",true);
		else
			responseBody.put("success", false);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	@GetMapping("/user/search/{username}")
	public List<User> searchByUsername(@PathVariable String username){
		return userService.searchUsers(username);
	}

}
