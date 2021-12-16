package com.inyoon.bookkureomi.user;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.inyoon.bookkureomi.domain.User;

public class MyAuthentication extends UsernamePasswordAuthenticationToken {

	Login principal;
	
	public MyAuthentication(String id, String password, List<GrantedAuthority> grantedAuthorityList, Login principal) {
		super(id, password, grantedAuthorityList);
		this.principal = principal;

	}
	
	public Login getUser() {
		return this.principal;
	}
	
	public void setPoint(int point) {
		this.principal.getUser().setPoint(point);
	}
	public int getPoint() {return this.principal.getUser().getPoint();}
}
