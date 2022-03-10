package com.tweetapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class Tweet {
    private String content;
    private LocalDateTime postedTime;
    private int userId;
    private int id;
}
