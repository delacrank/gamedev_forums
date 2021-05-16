package com.juan.gamedevforums.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.juan.gamedevforums.validation.ValidEmail;
import com.juan.gamedevforums.validation.ValidPassword;

public class AuthDto {

    public AuthDto(String email, String password ) {
	this.email = email;
	this.password = password;
    }
    
    
    @ValidEmail
    @NotNull
    @Size(min = 1, message = "At least 1 char")
    private String email;
    
    @ValidPassword
    @NotNull
    @Size(min = 1, message = "At least 1 char")
    private String password;

    public String getEmail() {
	return email;
    }

    public void setEmail(final String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(final String password) {
	this.password = password;
    }
}
