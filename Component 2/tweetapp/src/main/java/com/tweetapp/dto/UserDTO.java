package com.tweetapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
	@NotNull(message = "firstName is required")
	@NotEmpty(message = "firstName cannot be empty")
	private String firstName;
	@NotNull(message = "lastName is required")
	@NotEmpty(message = "lastName cannot be empty")
    private String lastName;
	@NotNull(message = "email is required")
	@NotEmpty(message = "email cannot be empty")
    private String email;
	@NotNull(message = "loginID is required")
	@NotEmpty(message = "loginID cannot be empty")
    private String loginID;
	@NotNull(message = "password is required")
	@NotEmpty(message = "password cannot be empty")
    private String password;
	@NotNull(message = "confirmPassword is required")
	@NotEmpty(message = "confirmPassword cannot be empty")
    private String confirmPassword;
	@NotNull(message = "contactNumber is required")
	@NotEmpty(message = "contactNumber cannot be empty")
    private String contactNumber;
	private String id;
}
