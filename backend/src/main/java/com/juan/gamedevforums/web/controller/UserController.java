package com.juan.gamedevforums.web.controller;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.ResponseEntity;
import com.juan.gamedevforums.web.error.UserAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameNotFoundException;
import com.juan.gamedevforums.persistence.model.User;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.web.dto.PasswordDto;
import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.util.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.juan.gamedevforums.web.error.StorageFileNotFoundException;
import com.juan.gamedevforums.storage.StorageService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

    private StorageService storageService; 
    
    @Autowired
    private IUserService userService;
    
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable final String username) {
	try {
	  userService.getUserByUsername(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UserNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	} 	
	return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/{username}/edit")
    public ResponseEntity<?> updateUserByUsername(
		    @PathVariable final String username,		    
		    @RequestBody final UserDto accountDto)
    {
	try {
	    userService.getUserByUsername(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UserNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}
        return new ResponseEntity<>(userService.updateUserByUsername(username, accountDto), HttpStatus.OK);	
    }    
}