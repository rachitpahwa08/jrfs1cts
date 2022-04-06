package com.tweetapp.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.tweetapp.dto.ReplyDTO;
import com.tweetapp.dto.TweetDTO;
import com.tweetapp.dto.UserDTO;
import com.tweetapp.model.Reply;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,imports = {LocalDateTime.class})
public interface TweetAppMapper {

	User userDTOtoUser(UserDTO userDTO);
	
	UserDTO userToUserDTO(User user);
	
	Tweet tweetDTOtoTweet(TweetDTO tweetDTO);
	
	@Mapping(target = "timestamp",expression = "java(LocalDateTime.now())")
	Reply replyDTOToReply(ReplyDTO replyDTO,String id,String userId);
}
