package com.tweetapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class User {
	@Id
	private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true,background = true)
    private String email;
    @Indexed(unique = true,background = true)
    private String loginID;
    private String password;
    private String contactNumber;
    @DBRef
    private List<Tweet> tweets;
}
