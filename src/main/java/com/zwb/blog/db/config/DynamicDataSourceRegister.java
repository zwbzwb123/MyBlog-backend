package com.zwb.blog.db.config;

import com.zwb.blog.aspect.DynamicDataSourceAspect;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicDataSourceRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    private Binder binder;

    private DataSource defaultDataSource;

    private Map<String, DataSource> slavesDataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        binder = Binder.get(environment);

        try {
            initDefaultDataSource();
            initSlavesDataSource();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initDefaultDataSource() throws ClassNotFoundException {
        Map config = binder.bind("spring.datasource.master", Map.class).get();
        defaultDataSource = buildDataSource(config);
    }

    public void initSlavesDataSource() throws ClassNotFoundException {
        List<Map> configs = binder.bind("spring.datasource.slave", Bindable.listOf(Map.class)).get();
        for (Map config : configs) {
            String name = config.get("name").toString();
            slavesDataSources.put(name, buildDataSource(config));
        }
    }

    @SuppressWarnings("unchecked")
    private DataSource buildDataSource(Map config) throws ClassNotFoundException {
        Object type = config.get("type");
        if (type == null) {
            type = DATASOURCE_TYPE_DEFAULT;
        }
        Class<? extends DataSource> clazz = (Class<? extends DataSource>) Class.forName((String) type);
        String driver = config.get("driver-class-name").toString();
        String password = config.get("password").toString();
        String url = config.get("url").toString();
        String username = config.get("username").toString();
        DataSource dataSource = DataSourceBuilder
                .create()
                .type(clazz)
                .driverClassName(driver)
                .password(password)
                .url(url)
                .username(username)
                .build();
        return dataSource;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        GenericBeanDefinition aspectBeanDefinition = new GenericBeanDefinition();
        aspectBeanDefinition.setBeanClass(DynamicDataSourceAspect.class);
        registry.registerBeanDefinition("dynamicDataSourceAspect",aspectBeanDefinition);
        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put("master", this.defaultDataSource);
        DynamicDataSourceContext.dataSources.add("master");

        targetDataSources.putAll(slavesDataSources);
        DynamicDataSourceContext.dataSources.addAll(slavesDataSources.keySet());

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
    }
}
