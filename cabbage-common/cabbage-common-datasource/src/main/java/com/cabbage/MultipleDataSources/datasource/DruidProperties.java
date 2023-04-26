package com.cabbage.MultipleDataSources.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.datasource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DruidProperties {
    private String type;
    private String driverClassName;
    private Map<String, Map<String, String>> ds;

    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;

    /**
     * 一会在外部构造一个DruidDataSource对象，但是这个对象只包含三种核心属性 url username password
     * 在这个方法中给对象设置公共属性
     *
     * @param dragSource
     * @return
     */
    public DataSource dataSource(DruidDataSource dragSource) {
        dragSource.setInitialSize(initialSize);
        dragSource.setMinIdle(minIdle);
        dragSource.setMaxActive(maxActive);
        dragSource.setMaxWait(maxWait);
        return dragSource;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getDriverClassName() {
//        return driverClassName;
//    }
//
//    public void setDriverClassName(String driverClassName) {
//        this.driverClassName = driverClassName;
//    }
//
//    public Map<String, Map<String, String>> getDs() {
//        return ds;
//    }
//
//    public void setDs(Map<String, Map<String, String>> ds) {
//        this.ds = ds;
//    }
//
//    public Integer getInitialSize() {
//        return initialSize;
//    }
//
//    public void setInitialSize(Integer initialSize) {
//        this.initialSize = initialSize;
//    }
//
//    public Integer getMinIdle() {
//        return minIdle;
//    }
//
//    public void setMinIdle(Integer minIdle) {
//        this.minIdle = minIdle;
//    }
//
//    public Integer getMaxActive() {
//        return maxActive;
//    }
//
//    public void setMaxActive(Integer maxActive) {
//        this.maxActive = maxActive;
//    }
//
//    public Integer getMaxWait() {
//        return maxWait;
//    }
//
//    public void setMaxWait(Integer maxWait) {
//        this.maxWait = maxWait;
//    }
////    private Integer timeBetweenEvictionRunsMillis;
////    private Integer minEvictableIdleTimeMillis;
////    private Integer maxEvictableIdleTimeMillis;
////    private String validateEviction;
////    private Boolean testWhileIdle;
////    private Boolean testOnBorrow;
////    private Boolean testOnReturn;
//

}
