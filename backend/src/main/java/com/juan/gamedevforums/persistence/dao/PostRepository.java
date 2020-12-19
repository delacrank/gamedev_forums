package com.juan.gamedevforums.persistence.dao;

import java.util.Set;

import com.juan.gamedevforums.persistence.model.Post;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select count(*) from post p, topic t where t.id = p.topic_id and categories_id = :id", nativeQuery = true)
    Long countByCategoriesId(@Param("id") Long categories_id);
    
    Set<Post> findByUser(User user);    
    Set<Post> findByTopic(Topic topic);
    Set<Post> findByTopicId(Long id);
    Set<Post> findAllByOrderByCreationDateDesc();    
    Set<Post> findTop5ByOrderByCreationDateDesc();
}

