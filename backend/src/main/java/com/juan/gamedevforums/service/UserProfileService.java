package com.juan.gamedevforums.service;

import javax.transaction.Transactional;

import com.juan.gamedevforums.persistence.model.UserProfile;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.service.UserService;
import com.juan.gamedevforums.service.PostService;
import com.juan.gamedevforums.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserProfileService implements IUserProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private TopicService topicService;

    @Override
    public UserProfile findOne(Long userId) {
	UserProfile userProfile = new UserProfile();
	User user = userService.findOne(userId);
	userProfile.setUser(user);
	userProfile.setPosts(postService.findByUser(user));
	userProfile.setTopics(topicService.findByUser(user));
	return userProfile;
    }

    @Override
    public UserProfile findOne(String username) {
	return findOne(userService.findUserByUsername(username).getId());
    }
}
