package com.zwb.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.zwb.blog.entity.Article;
import com.zwb.blog.entity.Label;
import com.zwb.blog.mapper.LabelMapper;
import com.zwb.blog.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LabelService implements ILabelService {

    @Autowired
    LabelMapper mapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    private final static String LABEL_REDIS_KEY = "label:all";

    @Override
    public List<Label> getLabels() {
        String ls = redisTemplate.opsForValue().get(LABEL_REDIS_KEY);
        if (!StringUtils.isEmpty(ls)){
            System.out.println("use cache get label");
            return JSON.parseObject(ls,List.class);
        }
        List<Label> labels = mapper.getLabels();
        redisTemplate.opsForValue().set(LABEL_REDIS_KEY, JSON.toJSONString(labels),3600, TimeUnit.SECONDS);
        return labels;
    }
}
