package com.tweetapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
	@NotNull(message = "email is required")
	@NotEmpty(message = "email cannot be empty")
	private String email;
	@NotNull(message = "password is required")
	@NotEmpty(message = "password cannot be empty")
	private String password;
}
