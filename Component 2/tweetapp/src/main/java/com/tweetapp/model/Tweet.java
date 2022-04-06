package com.tweetapp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Tweet {
	private String content;
    private LocalDateTime postedTime;
    private String userName;
    @Id
    private String id;
    private List<Reply> replies;
    public Tweet(String content,String userName) {
    	this.content=content;
    	this.postedTime=LocalDateTime.now();
    	this.userName=userName;
    }
}
