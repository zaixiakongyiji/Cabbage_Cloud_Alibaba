package com.cabbage.MultipleDataSources.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@EnableConfigurationProperties(DruidProperties.class)
public class LoadDataSource {
    @Resource
    DruidProperties druidProperties;

    public Map<String, DataSource> loadAllDataSources() {
        Map<String, DataSource> map = new HashMap<>();
        Map<String, Map<String, String>> ds = druidProperties.getDs();
        log.info(String.valueOf(druidProperties.getDs()));
        Set<String> keySet = ds.keySet();
        try {
            for (String key : keySet) {
                map.put(key, druidProperties.dataSource((DruidDataSource) DruidDataSourceFactory.createDataSource(ds.get(key))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
