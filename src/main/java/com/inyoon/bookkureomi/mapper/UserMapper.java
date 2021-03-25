package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User getUser(int userNo);
    public void insertUser(User user);
    public void updateUser(int userNo, User user);
    public void deleteUser(int userNo);
    public void login(User user);
}
