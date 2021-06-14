package com.inyoon.bookkureomi.user;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.inyoon.bookkureomi.domain.User;

public class MyAuthentication extends UsernamePasswordAuthenticationToken {

	User user;
	
	public MyAuthentication(String id, String password, List<GrantedAuthority> grantedAuthorityList, User user) {
		super(id, password, grantedAuthorityList);
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
}
