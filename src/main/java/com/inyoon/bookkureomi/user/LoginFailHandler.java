package com.inyoon.bookkureomi.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String msg = exception.getMessage();

		response.setContentType("application/json"); 
    	response.setCharacterEncoding("utf-8"); 
    	String data = " { \"response\" : {"+ 
    					" \"error\" : true , "+ 
    					" \"message\" : \""+exception.getMessage()+"\"} } "; 
    	PrintWriter out = response.getWriter(); 
    	out.print(data); 
    	out.flush(); 
    	out.close(); 
	}

}
