package com.zwb.blog.common;

import com.zwb.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

public class ArticleCountCache {

    private static final ConcurrentHashMap<Integer,Integer> count =
            new ConcurrentHashMap<>();

    public static void incr(Integer articleId){
        Integer c;
        if ((c = count.get(articleId) ) == null){
            count.put(articleId,1);
        }else {
            count.replace(articleId,++c);
        }
    }

    public static void put(Integer id,Integer c){
        count.put(id,c);
    }

    public static Integer get(Integer articleId){
        Integer c;
        if ((c = count.get(articleId) ) == null)
            return 0;
        return c;
    }

    @Component
    private static class CountUpdateTask{

        @Autowired
        ArticleMapper mapper;

        @Scheduled(fixedRate = 1000*60)
        public void update(){
            count.forEach((id,value) ->
                mapper.updateCount(id,value)
            );
        }
    }
}
