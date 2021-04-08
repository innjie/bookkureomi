package com.inyoon.bookkureomi.domain;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public @Data class User implements UserDetails {
    private int userNo;
    private String id;
    private String name;
    private String pw;
    private String phone;
    private int firstGenre;
    private int secondGenre;
    private int thirdGenre;

    private String userAuth;
    private boolean enabled;

    ArrayList<GrantedAuthority> auth = new ArrayList<>();

    public User(String id, String password, List<GrantedAuthority> auth) {
        this.id = id;
        this.pw = password;
        this.auth = (ArrayList<GrantedAuthority>) auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(userAuth));
        return auth;
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
