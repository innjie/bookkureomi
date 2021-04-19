package com.inyoon.bookkureomi.user;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Resource
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;

        UserDetails userInfo = userService.loadUserByUsername(authToken.getName());
        if (userInfo == null) {
            throw new UsernameNotFoundException(authToken.getName());
        }

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userInfo.getAuthorities();

        return new UsernamePasswordAuthenticationToken(userInfo,null,authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
