package com.xcl.webchat.core.database.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :xiaochanglu
 * @Description :存放数据源对应的key
 * @date :2019/3/16 15:33
 */
public class DynamicDataSourceHolder {
    /**
     * 线程本地环境
     */
    private static final ThreadLocal<String> contextHolders = new ThreadLocal<String>();

    /**
     * 数据源列表
     */
    public static List<String> dataSourceIds = new ArrayList<String>();

    /**
     * 设置数据源
     */
    public static void setDataSource(String customerType) {
        contextHolders.set(customerType);
    }

    /**
     * 获取数据源
     */
    public static String getDataSource() {
        return (String) contextHolders.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        contextHolders.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
