package com.juan.gamedevforums.service;

import javax.transaction.Transactional;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.juan.gamedevforums.web.error.CategoriesNotFoundException;
import com.juan.gamedevforums.web.error.TopicNotFoundException;
import com.juan.gamedevforums.web.error.PostNotFoundException;
import com.juan.gamedevforums.service.TopicService;
import com.juan.gamedevforums.service.UserService;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Topic;
import com.juan.gamedevforums.persistence.model.Post;
import com.juan.gamedevforums.persistence.dao.PostRepository;

@Service
@Transactional
public class PostService implements IPostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private UserService userService;
    
    @Override
    public Post findOne(Long id) {
        // todo fix optional
        return postRepository.findById(id)
	    .orElseThrow(() -> new PostNotFoundException());
    }

    @Override
    public Long countByCategoriesId(Long categoriesId) {
    	return postRepository.countByCategoriesId(categoriesId);
    }
    
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    
    @Override
    public Set<Post> findRecent() {
        return postRepository.findTop5ByOrderByCreationDateDesc();
    }
    
    @Override
    public Set<Post> findAllByOrderByCreationDateDesc() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }
    
    @Override
    public Set<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }
    
    @Override
    public Set<Post> findByTopic(Topic topic) {
        return postRepository.findByTopic(topic);
    }
    
    @Override
    public Set<Post> findByTopic(Long id) {
	Optional<Post> post = postRepository.findById(id);
	if(post == null ) {
	    throw new PostNotFoundException();
	}
	return findByTopic(topicService.findOne(id));
    }
    
    @Override
    public void save(Post post) {
        postRepository.save(post);
    }
    
    @Override
    public void delete(Long id) {
        delete(findOne(id));
    }
    
    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
    
    @Override
    public void save(String content,
                     String username,
                     Long id) {
        Post post = new Post();
        post.setTopic(topicService.findOne(id));
        post.setUser(userService.findUserByUsername(username));
        post.setContent(content);
        save(post);
    }
    
}
