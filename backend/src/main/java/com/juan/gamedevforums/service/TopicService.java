package com.juan.gamedevforums.service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.service.CategoriesService;
import com.juan.gamedevforums.persistence.model.Categories;
import com.juan.gamedevforums.persistence.model.Topic;
import com.juan.gamedevforums.persistence.dao.TopicRepository;
import com.juan.gamedevforums.web.error.CategoriesNotFoundException;
import com.juan.gamedevforums.web.error.TopicNotFoundException;

@Service
@Transactional
public class TopicService implements ITopicService {
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private CategoriesService categoriesService;
    
    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
    
    @Override
    public Topic findOne(Long id) {
	Topic topic = topicRepository.findById(id).get();
	if(topic == null) {
	    throw new TopicNotFoundException("Topic not Found");
	}
	return topicRepository.findById(id).get();
    }
    
    @Override
    public Set<Topic> findRecent() {
        return topicRepository.findTop5ByOrderByCreationDateDesc();
    }
    
    @Override
    public Set<Topic> findAllByOrderByCreationDateDesc() {
        return topicRepository.findAllByOrderByCreationDateDesc();
    }
    
    @Override
    public Set<Topic> findByCategories(Categories categories) {
	Set<Topic> topic = this.topicRepository.findByCategories(categories);
	if(topic == null || topic.isEmpty()) {
	    throw new CategoriesNotFoundException("Category not Found");
	}
        return topic;
    }
    
    @Override
    public Set<Topic> findByCategories(String categories) {
        return findByCategories(categoriesService.findByName(categories));
    }

    @Override
    public Long countByCategoriesId(Long categoriesId) {
	return topicRepository.countByCategoriesId(categoriesId);
    }
    
    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }
    
    @Override
    public Set<Topic> findByCategories(Long id) {
        return findByCategories(categoriesService.findOne(id));
    }
    
    @Override
    public Set<Topic> findByUser(User user) {
        return topicRepository.findByUser(user);
    }
    
    @Override
    public void delete(Long id) {
        delete(findOne(id));
    }
    
    @Override
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }

}
