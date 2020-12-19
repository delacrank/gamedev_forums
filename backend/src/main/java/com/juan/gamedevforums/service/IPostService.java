package com.juan.gamedevforums.service;

import com.juan.gamedevforums.persistence.model.User;

import java.util.List;
import java.util.Set;

import com.juan.gamedevforums.persistence.model.Post;
import com.juan.gamedevforums.persistence.model.Topic;


public interface IPostService {
    
    Post findOne(Long id);

    Long countByCategoriesId(Long categoriesId);
    
    List<Post> findAll();
    
    Set<Post> findRecent();
    
    Set<Post> findAllByOrderByCreationDateDesc();
    
    Set<Post> findByUser(User user);   

    Set<Post> findByTopic(Long id);
    
    Set<Post> findByTopic(Topic topic);
    
    void save(Post post);
    
    void delete(Long id);
    
    void delete(Post post);
    
    void save(String content,
              String username,
              Long id);
    
}
