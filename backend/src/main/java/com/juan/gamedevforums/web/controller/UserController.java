package com.juan.gamedevforums.web.controller;

import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;

import com.juan.gamedevforums.web.error.UserAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameNotFoundException;
import com.juan.gamedevforums.persistence.model.User;

import com.juan.gamedevforums.persistence.model.UserProfile;
import com.juan.gamedevforums.service.IUserProfileService;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.web.dto.PasswordDto;
import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.dto.AuthDto;
import com.juan.gamedevforums.web.util.GenericResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import com.juan.gamedevforums.security.MyUserDetailsService;
import com.juan.gamedevforums.web.error.StorageFileNotFoundException;
import com.juan.gamedevforums.storage.StorageService;
import com.juan.gamedevforums.web.util.JwtTokenUtil;
import com.juan.gamedevforums.web.util.JwtResponse;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
    
    private StorageService storageService; 

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private IUserService userService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final AuthDto authDto ) {
	try {
	    final Authentication authentication =  authenticationManager.authenticate(
			      new UsernamePasswordAuthenticationToken(
						      authDto.getEmail(),
						      authDto.getPassword()
			      )
            );
	    SecurityContextHolder.getContext().setAuthentication(authentication);	
	} catch (BadCredentialsException ex) {
	    GenericResponse message = new GenericResponse("Invalid Credentials", "InvalidCredentials");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.FORBIDDEN);
	}

	final UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
	final String jwt = jwtTokenUtil.generateToken(userDetails);
	return ResponseEntity.ok(new JwtResponse(jwt));
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable final String username)
    {
	try {
	    userService.getUserByUsername(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UsernameNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }
		
    @GetMapping("/userprofile")
    public ResponseEntity<?> getUserProfile(Authentication authentication)
    {
        String username = ((User)authentication.getPrincipal()).getUsername();
	try {
	  userService.getUserByUsername(username);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("User not found", "UserNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	} 	

       return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);     
    }

    @PutMapping("/userprofile/edit")
    public ResponseEntity<?> updateUserByUsername(
		    @RequestBody final UserDto accountDto,
                    Authentication authentication)
    {
	String username2 = ((User)authentication.getPrincipal()).getUsername();
	try {
	    userService.updateUserByUsername(username2, accountDto);
	} catch (final UsernameNotFoundException unfe) {
	    GenericResponse message = new GenericResponse("Username not found", "UsernameNotFound");
	    return new ResponseEntity<GenericResponse>(message, HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<>("Success", HttpStatus.OK);    
    }    
}
