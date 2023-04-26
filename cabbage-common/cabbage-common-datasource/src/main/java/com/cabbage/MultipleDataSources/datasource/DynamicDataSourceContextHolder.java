package com.cabbage.MultipleDataSources.datasource;

/**
 * 存储当前线程所使用的数据源名称
 */
public class DynamicDataSourceContextHolder {
    public static ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static String getDateSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void setDateSourceType(String dsType) {
        CONTEXT_HOLDER.set(dsType);
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
