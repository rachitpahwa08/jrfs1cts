package com.tweetapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String email;
    private String password;
    private List<Tweet> tweets;

    public User(UserAndTweet userAndTweet,List<Tweet> tweets){
        this.tweets=tweets;
        this.id=userAndTweet.getUserId();
        this.firstName=userAndTweet.getFirstName();
        this.lastName=userAndTweet.getLastName();
        this.gender=userAndTweet.getGender();
        this.dob=userAndTweet.getDob();
        this.email=userAndTweet.getEmail();
    }
}
