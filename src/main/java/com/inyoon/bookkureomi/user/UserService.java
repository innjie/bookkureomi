package com.inyoon.bookkureomi.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.inyoon.bookkureomi.domain.User;


public interface UserService extends UserDetailsService {

    public User getUser(int userNo);
    public User getUserById(String id);
    public int getUserNo();
    public void insertUser(User user);
    public void updateUser(int userNo, User user);
    public void deleteUser(int userNo);
    public String login(User user);
    public void logout(User user);
    
    UserDetails loadUserByUsername(String id);

}
