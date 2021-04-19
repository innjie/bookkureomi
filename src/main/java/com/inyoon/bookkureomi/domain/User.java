package com.inyoon.bookkureomi.domain;

import com.sun.istack.Nullable;
import lombok.Data;
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
    private List<String> genreArray;
    @Nullable
    private int firstGenre;
    @Nullable
    private int secondGenre;
    @Nullable
    private int thirdGenre;
    private int point;
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

    public User(int userNo, String id, String name, String pw, String confirmPw, String phone, List<String> genreArray,
			String auth, boolean enabled, String userRole, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userNo = userNo;
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.confirmPw = confirmPw;
		this.phone = phone;
		this.genreArray = genreArray;
		this.auth = auth;
		this.enabled = enabled;
		this.userRole = userRole;
		this.authorities = authorities;
	}

    public User(String id, String pw, String name, String phone, List<String> genreArray) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.genreArray = genreArray;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("USER"));
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
