package com.cabbage.MultipleDataSources.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(LoadDataSource loadDataSource) {
        //1设置所有数据源
        Map<String, DataSource> allDs = loadDataSource.loadAllDataSources();
        super.setTargetDataSources(new HashMap<>(allDs));
        //2设置默认数据源
        //并不是所有方法上都有注解 对于没有注解的方法，使用默认数据源
        super.setDefaultTargetDataSource(allDs.get(DataSourceType.DEFAULT_DS_NAME));
        //3
        super.afterPropertiesSet();
    }

    /**
     * 这个方法用来返回数据源名称，当系统需要获取数据源时 会自动调用这些方法获取数据源名称
     *
     * @return
     */
    @Override
    public Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDateSourceType();
    }
}
