package com.zwb.blog.common;

import com.zwb.blog.entity.Email;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CaptchaCache {

    private static final ConcurrentHashMap<String, Email> captchas;

    static {
        captchas = new ConcurrentHashMap<>();
    }

    public static void put(String email,Email code){
        captchas.put(email,code);
    }

    public static Email get(String email){
        return captchas.get(email);
    }

    public static void remove(String email){
        captchas.remove(email);
    }


    @Component
    private static class CheckTask{

        private int DEFAULT_GAP = 1000*60*5;

        @Scheduled(fixedRate = 1000*60)
        public void check(){
            Iterator<Map.Entry<String, Email>> iterator = captchas.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Email> entry = iterator.next();
                Date createTime = entry.getValue().getCreateTime();
                if (System.currentTimeMillis() - createTime.getTime() >= DEFAULT_GAP){
                    iterator.remove();
                }
            }
        }
    }
}
