package com.tweetapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TweetDTO {
	@NotNull(message = "content is required")
	@NotEmpty(message = "content cannot be empty")
	private String content;
}
