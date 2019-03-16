package com.xcl.webchat.core.database.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author :xiaochanglu
 * @Description : 根据Key切换数据源
 * @date :2019/3/16 15:35
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}
