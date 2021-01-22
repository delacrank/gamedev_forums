package com.juan.gamedevforums.web.dto.dtoConverter;

import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Topic;
import com.juan.gamedevforums.persistence.model.Post;
import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.dto.TopicDto;
import com.juan.gamedevforums.web.dto.PostDto;

import java.util.Optional;

public final class ToDtoConverter {

    public static UserDto userToDto(final User user) {
	return new UserDto(
			   user.getEmail(),
			   user.getUsername(),
			   user.isEnabled(),
			   user.getImage(),
			   user.getBio()
			   );
    }

    public static TopicDto topicToDto(final Topic topic) {
	return new TopicDto(
			 topic.getId(),
			 topic.getUser(),
			 topic.getCategories(),
			 topic.getTitle(),
			 topic.getContent(),
			 topic.getViews(),
			 topic.getCreationDate(),
			 topic.getLastUpdateDate(),
			 topic.isClosed(),
			 userToDto(topic.getUser())
			 );
				   
    }

    
    public static PostDto postToDto(final Post post) {
	return new PostDto(
			   post.getId(),
			   post.getTopic(),
			   userToDto(post.getUser()),
			   post.getContent(),
			   post.getCreationDate(),
			   post.getLastUpdateDate()   			   
			   );
    }

	
    // public static UserProfileDto userProfileToDto(final UserProfile userProfile) {

    // }



}
