package com.tweetapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {
	@NotNull(message = "newPassword is required")
	@NotEmpty(message = "newPassword cannot be empty")
	private String newPassword;
	@NotNull(message = "confirmPassword is required")
	@NotEmpty(message = "confirmPassword cannot be empty")
	private String confirmPassword;
}
