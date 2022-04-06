package com.tweetapp.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO{

	/**
	 * 
	 */
	private final String statusCode;
	private final String message;
	private final String endpoint;
	private final LocalDateTime timeStamp;
	public ErrorDTO(String statusCode, String message, String endpoint, LocalDateTime timeStamp) {
		this.statusCode = statusCode;
		this.message = message;
		this.endpoint = endpoint;
		this.timeStamp = timeStamp;
	}
	
	
}
