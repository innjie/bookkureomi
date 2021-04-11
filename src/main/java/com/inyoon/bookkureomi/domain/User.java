package com.inyoon.bookkureomi.domain;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public @Data class User implements UserDetails {
    private int userNo;
    private String id;
    private String name;
    private String pw;
    private String confirmPw;
    private String phone;
//    private int firstGenre;
//    private int secondGenre;
//    private int thirdGenre;
    private Set<String> genres;
    private String auth;
    private boolean enabled;
    private String userRole;
    private Collection<? extends GrantedAuthority> authorities;

    public User(String id, String password, String auth) {
        this.id = id;
        this.pw = password;
        this.auth =  auth;
    }
    
    public User() {
    	
    }
    public User(int userNo, String id, String name, String pw, String confirmPw, String phone, Set<String> genres,
			String auth, boolean enabled, String userRole, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userNo = userNo;
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.confirmPw = confirmPw;
		this.phone = phone;
		this.genres = genres;
		this.auth = auth;
		this.enabled = enabled;
		this.userRole = userRole;
		this.authorities = authorities;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
