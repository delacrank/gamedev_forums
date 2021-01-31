package com.juan.gamedevforums.web.controller;

import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import com.juan.gamedevforums.web.error.UserAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameNotFoundException;
import com.juan.gamedevforums.persistence.model.User;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.juan.gamedevforums.persistence.model.UserProfile;
import com.juan.gamedevforums.service.IUserProfileService;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.web.dto.PasswordDto;
import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.util.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

import com.juan.gamedevforums.web.error.StorageFileNotFoundException;
import com.juan.gamedevforums.storage.StorageService;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
    
    private StorageService storageService; 
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @RequestMapping("/login")
    // public boolean login(@RequestBody final UserDto userDto) {

    // 	final User user2;
    // 	try {
    // 	    user2 = userService.findUserByUsername(userDto.getUsername());
    // 	} catch(final UsernameNotFoundException unfe) {
    // 	    return false;
    // 	}
    //     return userDto.getUsername().equals(user2.getUsername()) && passwordEncoder.matches(userDto.getPassword(), user2.getPassword());
    // }

    // @RequestMapping()
    // public Principal user(HttpServletRequest request) {
    //     String authToken = request.getHeader("Authorization")
    //       .substring("Basic".length()).trim();
    //     return () ->  new String(Base64.getDecoder()
    //       .decode(authToken)).split(":")[0];
    // }
    
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(
					    @PathVariable final String username,
					    Authentication authentication)
    {
	try {
	    userProfileService.findOne(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UsernameNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<>(userProfileService.findOne(username), HttpStatus.OK);
    }
		
    @GetMapping("/userprofile/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable final String username,
					       Authentication authentication)
    {
	System.out.println(((User) authentication.getPrincipal()).getRoles());
	try {
	  userService.getUserByUsername(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UserNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	} 	

	String authUsername = ((User) authentication.getPrincipal()).getUsername();

	if(username.equals(authUsername)) {
	    try {
		userService.getUserByUsername(username);
	    } catch (AccessDeniedException ade) {
		GenericResponse message = new GenericResponse("Auth Error", "Auth Error");
		return new ResponseEntity<GenericResponse>(message, HttpStatus.FORBIDDEN);
	    }
	    return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
	}
	return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);	    
    }

    @PutMapping("/userprofile/{username}/edit")
    public ResponseEntity<?> updateUserByUsername(
		    @PathVariable final String username,		    
		    @RequestBody final UserDto accountDto,
                    Authentication authentication)
    {
	try {
	    userService.getUserByUsername(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UserNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}

	String authUsername = ((User) authentication.getPrincipal()).getUsername();

	if(username.equals(authUsername)) {
	    try {
		userService.updateUserByUsername(username, accountDto);
	    } catch (AccessDeniedException ade) {
		GenericResponse message = new GenericResponse("Auth Error", "Auth Error");
		return new ResponseEntity<GenericResponse>(message, HttpStatus.FORBIDDEN);
	    }
	    return new ResponseEntity<>("Success", HttpStatus.OK);
	} 
	return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);	    
    }    
}
