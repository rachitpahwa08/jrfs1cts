package com.tweetapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetAppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4913867961884194817L;
	private final String message;
	private final String code;
	public TweetAppException(String message, String code) {
		super(message);
		this.message = message;
		this.code = code;
	}
	
}
