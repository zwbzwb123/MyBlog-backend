package com.zwb.blog.mapper;

import com.zwb.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectUserByName(String username);

    void addUser(User user);

    User getUserByUsernameAndPassword(String username, String password);
}
