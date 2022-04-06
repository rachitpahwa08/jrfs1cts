package com.tweetapp.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleValidationErrorException(MethodArgumentNotValidException ex,HttpServletRequest request){
		StringBuilder details = new StringBuilder();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.append(error.getDefaultMessage());
			details.append(",");
		}
		ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST.toString(), details.toString(), request.getRequestURI(), LocalDateTime.now());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler({TweetAppException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleTweetAppException(TweetAppException ex,HttpServletRequest request){
		ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getRequestURI(), LocalDateTime.now());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}
}
