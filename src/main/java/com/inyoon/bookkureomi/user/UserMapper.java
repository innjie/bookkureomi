package com.inyoon.bookkureomi.user;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.User;

@Mapper
public interface UserMapper {
    public User getUser(int userNo);
    public User getUserById(String id);
    public int getUserNo();
    public void insertUser(User user);
    public void updateUser(User user);
    public void deleteUser(int userNo);
    public String login(User user);
    public void logout(User user);
}
