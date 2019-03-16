package com.xcl.webchat.core.database.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :xiaochanglu
 * @Description : 注解 数据源
 * 自定义切换数据源
 * @date :2019/3/16 15:32
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String dataSource();
}
