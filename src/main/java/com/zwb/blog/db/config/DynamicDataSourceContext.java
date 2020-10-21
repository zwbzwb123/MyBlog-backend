package com.zwb.blog.db.config;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContext {

    //用于存储所有的数据源
    public static List<String> dataSources = new ArrayList<>();

    //当前线程所使用的数据源
    private static ThreadLocal<String> currentDataSource = new ThreadLocal<>();

    public static String getCurrentDataSource(){
        return currentDataSource.get();
    }

    public static void setDataSource(String dataSource){
        currentDataSource.set(dataSource);
    }

    public static boolean containDataSource(String dataSource){
        return dataSources.contains(dataSource);
    }

    public static void removeCurrentDataSource(){
        currentDataSource.remove();
    }


}

