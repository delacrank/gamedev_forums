package com.juan.gamedevforums.web.dto;

import java.util.Set;

import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Post;
import com.juan.gamedevforums.persistence.model.Topic;

public class UserProfileDto {

    public UserProfileDto(User user, Set<Post> posts,
			  Set<Topic> topics ){
	this.user = user;
	this.posts = posts;
	this.topics = topics;
    }

    private User user;

    private Set<Post> posts;

    private Set<Topic> topics;

    

}


