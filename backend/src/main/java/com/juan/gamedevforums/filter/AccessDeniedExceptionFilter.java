package com.juan.gamedevforums.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

public class AccessDeniedExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request,
				 HttpServletResponse response, 
				 FilterChain fc) throws ServletException, IOException {
        try {
            fc.doFilter(request, response);
        } catch (AccessDeniedException e) {
	    RequestDispatcher requestDispatcher = 
		request.getServletContext().getRequestDispatcher("/login");
	    requestDispatcher.forward(request, response);
	}
    }

}
