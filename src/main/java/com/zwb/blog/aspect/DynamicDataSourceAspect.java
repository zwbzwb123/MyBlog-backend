package com.zwb.blog.aspect;


import com.zwb.blog.db.DataSource;
import com.zwb.blog.db.config.DynamicDataSourceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(-1)
public class DynamicDataSourceAspect {

    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint joinPoint, DataSource dataSource) {
        String dbName = dataSource.value();
        if (!DynamicDataSourceContext.containDataSource(dbName)) {
            System.out.println("not exist");
        } else {
            System.out.println("exist " + dbName);
            DynamicDataSourceContext.setDataSource(dbName);
        }
    }

    @After("@annotation(dataSource)")
    public void clearDataSource(JoinPoint joinPoint, DataSource dataSource) {
        System.out.println("clear");
        DynamicDataSourceContext.removeCurrentDataSource();
    }
}

