package com.zwb.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Autowired
    StringRedisTemplate template;

    @RequestMapping("redis")
    public String redisTest(){
        String stu = template.opsForValue().get("stu");
        return stu;
    }
}
