package com.zwb.blog.service.impl;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.User;
import com.zwb.blog.mapper.UserMapper;
import com.zwb.blog.service.IUserService;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper mapper;

    @Override
    public Result addUser(User user) {
        try {
            User u = mapper.selectUserByName(user.getUsername());
            if (u != null){
                return Result.failed().put("msg","该用户名已存在");
            }
            mapper.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error().put("msg","注册异常，请稍后再试");
        }
        return Result.ok().put("msg","注册成功");
    }

    @Override
    public User getUser(String username, String password) {
        return  mapper.getUserByUsernameAndPassword(username,password);
    }
}
