package com.juan.gamedevforums.service;

import java.util.List;
import java.util.Set;

import com.juan.gamedevforums.persistence.model.Categories;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Topic;

public interface ITopicService {
    
    List<Topic> findAll();

    Long countByCategoriesId(Long categoriesId);
    
    Topic findOne(Long id);
    
    Set<Topic> findRecent();
    
    Set<Topic> findAllByOrderByCreationDateDesc();
    
    Set<Topic> findByCategories(Categories categories);
    
    Set<Topic> findByCategories(String categoriesName);
    
    Topic save(Topic topic);
    
    Set<Topic> findByCategories(Long id);
    
    Set<Topic> findByUser(User user);
    
    void delete(Long id);
    
    void delete(Topic topic);

}
