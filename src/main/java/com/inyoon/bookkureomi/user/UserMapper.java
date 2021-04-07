package com.inyoon.bookkureomi.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User getUser(int userNo);
    public User getUserById(String id);
    public void insertUser(User user);
    public void updateUser(int userNo, User user);
    public void deleteUser(int userNo);
    public void login(User user);
    public void logout(User user);
}
