package com.cabbage.MultipleDataSources.annotation;


import com.cabbage.MultipleDataSources.datasource.DataSourceType;

import java.lang.annotation.*;

/**
 * 这个注解将来可以加在某一个service类或方法上，通过value属性来指定类或方法来使用那个数据源
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    /**
     * 如果一个方法上加了注解 但是没有指定数据源名称 默认使用master数据源
     */
    String value() default DataSourceType.DEFAULT_DS_NAME;
}
