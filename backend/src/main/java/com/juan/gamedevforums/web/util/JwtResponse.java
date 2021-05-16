package com.juan.gamedevforums.web.util;

public class JwtResponse {

    private String jwtToken;

    public JwtResponse() {}

    public JwtResponse(String jwtToken) {
	this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
	return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
	this.jwtToken = jwtToken;
    }
}
