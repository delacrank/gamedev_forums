package com.juan.gamedevforums.service;

import com.juan.gamedevforums.persistence.model.UserProfile;

public interface IUserProfileService {

    public UserProfile findOne(Long userId);

    public UserProfile findOne(String username);

}
