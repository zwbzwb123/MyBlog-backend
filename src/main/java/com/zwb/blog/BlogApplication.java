package com.zwb.blog;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@MapperScan("com.zwb.blog.mapper")
@EnableAspectJAutoProxy
public class BlogApplication {

    @Autowired
    StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);

    }

    @Bean
    public ApplicationRunner runner(){
        return args -> {
            redisTemplate.opsForValue().set("stu","zwb");
        };
    }
}
