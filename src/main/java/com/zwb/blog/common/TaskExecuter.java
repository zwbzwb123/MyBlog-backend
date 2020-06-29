package com.zwb.blog.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecuter {

    private static ThreadPoolExecutor executor;

    static {
        //
        executor =
                new ThreadPoolExecutor(
                        1,
                        1,
                        0L,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(5),
                        new ThreadPoolExecutor.DiscardPolicy());
    }

    public static ThreadPoolExecutor get(){
        return executor;
    }
}
