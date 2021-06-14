package com.inyoon.bookkureomi.domain;


import lombok.Data;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public @Data class User implements UserDetails {
	private int userNo;
    private String id;
    private String name;
    private String password;
    private String confirmPw;
    private String phone;
    private int point;
    private List<String> genreArray;
    @Nullable
    private int firstGenre;
    @Nullable
    private int secondGenre;
    @Nullable
    private int thirdGenre;
    
    private String userRole;
    
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isEnabled = true;
    private String username;
    private boolean isCredentialsNonExpired = true;
    private boolean isAccountNonExpired  = true;
    private boolean isAccountNonLocked  = true;
}
