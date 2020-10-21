package com.zwb.blog.db;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    String value() default "master";

    int loadBalance() default 1;

    class LoadBanlance{
        int ROUND_ROBIN = 1;
        int WEIGHT_RANDOM = 2;
    }
}
