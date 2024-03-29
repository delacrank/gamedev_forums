package com.juan.gamedevforums.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.juan.gamedevforums.web.util.JwtTokenUtil;
import com.juan.gamedevforums.security.MyUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
				    HttpServletResponse response,
				    FilterChain chain) throws ServletException, IOException {

	final String authorizationHeader = request.getHeader("Authorization");

	String email = null;
	String jwt = null;

	if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	    jwt = authorizationHeader.substring(7);
	    email = jwtUtil.getEmailFromToken(jwt);
	}

	if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
	    if(jwtUtil.validateToken(jwt, userDetails)) {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	    }
	}
	chain.doFilter(request, response);
    }    
}
