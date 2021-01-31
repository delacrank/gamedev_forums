package com.juan.gamedevforums.web.controller;

import javax.validation.Valid;
import java.security.Principal;
import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.AccessDeniedException;

import com.juan.gamedevforums.web.error.TopicNotFoundException;
import com.juan.gamedevforums.web.error.TopicAlreadyExistException;
import com.juan.gamedevforums.web.error.CategoriesNotFoundException;

import com.juan.gamedevforums.web.util.GenericResponse;
import com.juan.gamedevforums.persistence.model.Post;
import com.juan.gamedevforums.persistence.model.Topic;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.service.IPostService;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.service.ICategoriesService;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.service.ITopicService;
import com.juan.gamedevforums.web.dto.TopicDto;

@RestController
@RequestMapping("/api/forum/")
@CrossOrigin(origins="http://localhost:4200")
public class TopicController {
    
    @Autowired
    private IPostService postService;
    
    @Autowired
    private ITopicService topicService;
    
    @Autowired
    private ICategoriesService categoriesService;
    
    @Autowired
    private IUserService userService;

    @GetMapping("/{catName}/{id}")
    public ResponseEntity<?> getPostByTopicId(
					      @PathVariable final String catName,
					      @PathVariable final Long id)
    {
	try {
	    topicService.findByCategories(catName);
	} catch (final CategoriesNotFoundException cnfe) {
    	    GenericResponse message = new GenericResponse("Category not found", "CategoryNotFound");
    	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}

	try {
	    topicService.findOne(id);	    
	} catch ( final TopicNotFoundException tnfe ) {
	    GenericResponse message = new GenericResponse("topic not found", "TopicNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}
	Topic topic = topicService.findOne(id);
	topic.setViews(topic.getViews() + 1);
	topicService.save(topic);
	return new ResponseEntity<>(postService.findByTopic(id), HttpStatus.OK);
    }
    
    @PostMapping("/{catName}/addTopic")
    public ResponseEntity<?> addTopic(
				     @Valid @RequestBody final TopicDto newTopicDto,
				     @PathVariable final String catName,
				     Authentication authentication)
    {

	try {
	    topicService.findByCategories(catName);
	} catch (final CategoriesNotFoundException cnfe) {
    	    GenericResponse message = new GenericResponse("Category not found", "CategoryNotFound");
    	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}

        final User user;
	try {
	    user = userService.findUserByUsername(((User) authentication.getPrincipal()).getUsername());
	} catch(NullPointerException e) {
	    GenericResponse message = new GenericResponse("Invalid Credentials", "InvalidCredentials");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.FORBIDDEN);
	}

	try {
	    Topic topic = new Topic();
	    topic.setUser(user);
	    topic.setTitle(newTopicDto.getTitle());
	    topic.setContent(newTopicDto.getContent());
	    topic.setCategories(categoriesService.findByName(catName));
	    topicService.save(topic);	 
	} catch (AccessDeniedException ade) {
	    GenericResponse message = new GenericResponse("Auth Error", "Auth Error");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.FORBIDDEN);
	}
	return new ResponseEntity<>("Success", HttpStatus.OK);

    }
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getPostByTopicId(@PathVariable final Long id) {	
    // 	try {
    // 	    postService.findByTopic(id);	   
    // 	} catch (final CategoriesNotFoundException tnfe) {
    // 	    GenericResponse message = new GenericResponse("topic not found", "TopicNotFound");
    // 	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
    // 	}	
    // 	Topic topic = topicService.findOne(id);
    // 	topic.setViews(topic.getViews() + 1);
    // 	topicService.save(topic);
    // 	return new ResponseEntity<>(postService.findByTopic(id), HttpStatus.OK);
    // }

    
    // @PostMapping("/id", method = RequestMethod.POST)
    // public String addPost(@Valid @ModelAttribute("newPost") NewPostForm newPost,
    //                       BindingResult result,
    //                       Authentication authentication,
    //                       @PathVariable int idTopic,
    //                       Model model) {
        
    //     if (result.hasErrors()) {
    //         model.addAttribute("topic", topicService.findOne(idTopic));
    //         model.addAttribute("posts", postService.findByTopic(idTopic));
    //         return "topic";
    //     }
        
    //     Post post = new Post();
    //     post.setContent(newPost.getContent());
    //     post.setTopic(topicService.findOne(idTopic));
    //     post.setUser(userService.findByUsername(authentication.getName()));
    //     postService.save(post);
        
    //     model.asMap().clear();
    //     return "redirect:/topic/" + idTopic;
    // }
    
    // @RequestMapping(value = "new", method = RequestMethod.GET)
    // public String getNewTopictForm(Model model) {
    //     model.addAttribute("newTopic", new NewTopicForm());
    //     model.addAttribute("categoriess", categoriesService.findAll());
    //     return "new_topic_form";
    // }
    
    // @PostMapping(/"new"
    // 		 ST)
    // public String processAndAddNewTopic(@Valid @ModelAttribute("newTopic") NewTopicForm newTopic,
    //                                     BindingResult result,
    //                                     Authentication authentication,
    //                                     Model model) {
        
    //     if (result.hasErrors()) {
    //         model.addAttribute("categoriess", categoriesService.findAll());
    //         return "new_topic_form";
    //     }
        
    //     Topic topic = new Topic();
    //     topic.setUser(userService.findByUsername(authentication.getName()));
    //     topic.setTitle(newTopic.getTitle());
    //     topic.setContent(newTopic.getContent());
    //     topic.setCategories(categoriesService.findOne(newTopic.getCategoriesId()));
    //     topicService.save(topic);
        
    //     return "redirect:/topic/" + topic.getId();
    // }
    
    // @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    // public String delete(@PathVariable int id,
    //                      Authentication authentication,
    //                      RedirectAttributes model) {
    //     Topic topic = topicService.findOne(id);
        
    //     if (topic == null) {
    //         return "redirect:/";
    //     }
    //     if (!authentication.getName().equals(topic.getUser().getUsername())) {
    //         return "redirect:/topic/" + id;
    //     }
        
    //     topicService.delete(topic);
        
    //     model.addFlashAttribute("message", "topic.successfully.deleted");
    //     return "redirect:/categories/" + topic.getCategories().getId();
    // }
    
}
