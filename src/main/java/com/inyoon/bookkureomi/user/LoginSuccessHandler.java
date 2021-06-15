package com.inyoon.bookkureomi.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//        System.out.println(authentication.getName());
//        session.setAttribute("greeting", authentication.getName());
//       response.sendRedirect("/book/");
        
        //String accept = request.getHeader("accept");  //json
        
        response.setContentType("application/json"); 
    	response.setCharacterEncoding("utf-8"); 
    	String data = " { \"response\" : {"+ 
    					" \"error\" : false , "+ 
    					" \"message\" : \"로그인하였습니다.\"} } "; 
    	PrintWriter out = response.getWriter(); 
    	out.print(data); 
    	out.flush(); 
    	out.close(); 
        
    }
}
