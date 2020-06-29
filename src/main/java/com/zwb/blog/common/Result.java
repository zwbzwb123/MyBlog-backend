package com.zwb.blog.common;


import java.util.HashMap;

public class Result extends HashMap<String,Object> {


    public static Result ok(){
        return new Result().put("code",Code.SUCCESS);
    }


    public static Result error(){
        return new Result().put("code",Code.ERROR);
    }

    public static Result failed(){
        return new Result().put("code",Code.FAILED);
    }

    public Result put(String key ,Object value){
        super.put(key,value);
        return this;
    }

    private Result(){}
}
