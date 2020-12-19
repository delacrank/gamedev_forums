package com.juan.gamedevforums.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.juan.gamedevforums.validation.PasswordMatches;
import com.juan.gamedevforums.validation.ValidEmail;
import com.juan.gamedevforums.validation.ValidPassword;

@PasswordMatches
public class UserDto {

    public UserDto(String email, String username,
		   boolean enabled, String image, String bio ) {
	this.email = email;
	this.username = username;
	this.enabled = enabled;
	this.image = image;
	this.bio = bio;
    }
    
    @NotNull
    @Size(min = 1, message = "{Size.userDto.userName}")
    private String username;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    private Boolean enabled;

    private String image;

    private String bio;

    private Integer role;

    public Integer getRole() {
	return role;
    }

    public void setRole(final Integer role) {
	this.role = role;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(final String email) {
	this.email = email;
    }
    
    public String getUsername() {
	return username;
    }

    public void setUsername(final String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(final String password) {
	this.password = password;
    }

    public String getMatchingPassword() {
	return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
	this.matchingPassword = matchingPassword;
    }   

    public boolean isEnabled() {
	return enabled;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public String getBio() {
	return bio;
    }

    public void setBio(String bio) {
	this.bio = bio;
    }
}
