package com.inyoon.bookkureomi.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.inyoon.bookkureomi.domain.User;

@Component
public class AuthProvider implements AuthenticationProvider{
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	 
	public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		String id = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		return authenticate(id,password);
	}
	
	public Authentication authenticate(String id, String password) throws AuthenticationException{
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

		Login principal = (Login) userService.loadUserByUsername(id);
		User pUser = principal.getUser();
		
		//일치 id 없음
		if(pUser == null) {
			throw new UsernameNotFoundException("wrongid");
		}

		//pw 입력 오류
		if(!passwordEncoder.matches(password, principal.getPassword())) {
			throw new BadCredentialsException("wrongpw");
		}
		
		principal.setUsername(pUser.getName());
		grantedAuthorityList.add(new SimpleGrantedAuthority(pUser.getUserRole()));

		return new MyAuthentication(id, password, grantedAuthorityList, principal);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
