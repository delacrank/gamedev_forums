package com.juan.gamedevforums.web.controller;

import javax.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.juan.gamedevforums.web.error.CategoriesNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.juan.gamedevforums.persistence.model.Role;
import com.juan.gamedevforums.persistence.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.juan.gamedevforums.web.util.GenericResponse;
import com.juan.gamedevforums.service.ICategoriesService;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.service.ITopicService;
import com.juan.gamedevforums.service.IPostService;

@RestController
@RequestMapping("/api/forum/")
@CrossOrigin(origins="http://localhost:4200")
public class CategoriesController {
      
    @Autowired
    private ITopicService topicService;

    @Autowired
    private ICategoriesService categoriesService;

    @Autowired
    private IPostService postService;

    @GetMapping()
    public ResponseEntity<List> getCategories() {
      return new ResponseEntity<List>(categoriesService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{catName}")
    public ResponseEntity<?> getTopicsFromCategories(@PathVariable final String catName) {
	try {
	  topicService.findByCategories(catName);
	} catch (final CategoriesNotFoundException cnfe) {
	    GenericResponse message = new GenericResponse("Category not found", "CategoryNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	} 	
	return new ResponseEntity<>(topicService.findByCategories(catName), HttpStatus.OK);
    }

    @GetMapping("/topic-count/{categoriesId}")
    public ResponseEntity<?> getTopicCountFromCategories(@PathVariable final Long categoriesId) {
    	try {
    	  topicService.countByCategoriesId(categoriesId);
    	} catch (final CategoriesNotFoundException cnfe) {
	    GenericResponse message = new GenericResponse("Category not found", "CategoryNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
    	} 	
    	return new ResponseEntity<>(topicService.countByCategoriesId(categoriesId), HttpStatus.OK);
    }

    @GetMapping("/post-count/{categoriesId}")
    public ResponseEntity<?> getPostCountFromCategories(@PathVariable final Long categoriesId) {
    	try {
    	  postService.countByCategoriesId(categoriesId);
    	} catch (final CategoriesNotFoundException cnfe) {
	    GenericResponse message = new GenericResponse("Category not found", "CategoryNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
    	} 	
    	return new ResponseEntity<>(postService.countByCategoriesId(categoriesId), HttpStatus.OK);
    }

}
