package com.juan.gamedevforums.persistence.dao;

import java.util.Set;

import com.juan.gamedevforums.persistence.model.Topic;
import com.juan.gamedevforums.persistence.model.Categories;
import com.juan.gamedevforums.persistence.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Long countByCategoriesId(Long categoriesId);
    //    Topic findOne(Long id);
    Set<Topic> findByCategories(Categories categories);
    Set<Topic> findByUser(User user);
    Set<Topic> findAllByOrderByCreationDateDesc();
    Set<Topic> findTop5ByOrderByCreationDateDesc();
}

