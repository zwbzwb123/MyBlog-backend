package com.zwb.blog.service;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.User;

public interface IUserService {

    Result addUser(User user);

    User getUser(String username, String password);
}
