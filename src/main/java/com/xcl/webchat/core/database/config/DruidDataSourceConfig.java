package com.xcl.webchat.core.database.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author :xiaochanglu
 * @Description : 多数据源切换
 * @date :2019/3/16 15:36
 */
@Configuration
public class DruidDataSourceConfig {
    /**
     * 配置别名
     */
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    /**
     * 配置mapper的扫描，找到所有的mapper.xml映射文件
     */
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    /**
     * 数据源1
     */
    @Bean(name = "oneDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源2
     */
    @Bean(name = "twoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源管理
     * 其实就是利用的 targetDataSources 进行存储了多个数据源信息
     */
    @Bean
    public DataSource dynamicDataSource() throws SQLException {
        DynamicDataSource dynmicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>(2);
        targetDataSources.put("oneDataSource", dataSourceOne());
        targetDataSources.put("twoDataSource", dataSourceTwo());
        dynmicDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        dynmicDataSource.setDefaultTargetDataSource(dataSourceOne());
        return dynmicDataSource;
    }

    /**
     * SqlSessionFactory
     * //加载  mybatis 配置文件
     * sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事物
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
