package com.inyoon.bookkureomi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(int userNo) {
        return userMapper.getUser(userNo);
    }
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }
    public void insertUser(User user) {
        user.setPw(passwordEncoder.encode(user.getPw()));
        userMapper.insertUser(user);
    }
    public void updateUser(int userNo, User user) {
        userMapper.updateUser(userNo, user);
    }
    public void deleteUser(int userNo) {
        userMapper.deleteUser(userNo);
    }
    public void login(User user) {
        userMapper.login(user);
    }
    public void logout(User user) {
        userMapper.logout(user);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userMapper.getUserById(id);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if(user != null) {
            authorities.add(new SimpleGrantedAuthority(user.getUserRole()));
            user.setAuthorities(authorities);
        }
        return user;
    }

}