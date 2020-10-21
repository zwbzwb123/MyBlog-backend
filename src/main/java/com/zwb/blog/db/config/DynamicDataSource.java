package com.zwb.blog.db.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("Current DataSource is " + DynamicDataSourceContext.getCurrentDataSource());
        return DynamicDataSourceContext.getCurrentDataSource();
    }
}
