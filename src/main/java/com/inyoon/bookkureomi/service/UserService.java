package com.inyoon.bookkureomi.service;

import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(int userNo) {
        return userMapper.getUser(userNo);
    }
    public void insertUser(User user) {
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

}
