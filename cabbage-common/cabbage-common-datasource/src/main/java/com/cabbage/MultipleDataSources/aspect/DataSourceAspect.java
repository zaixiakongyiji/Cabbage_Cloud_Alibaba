package com.cabbage.MultipleDataSources.aspect;

import com.cabbage.MultipleDataSources.annotation.DataSource;
import com.cabbage.MultipleDataSources.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DataSourceAspect {
    /**
     * @annotation(com.qiren.biz.annotation.DataSource) 方法上有@DataSource注解拦截下来
     * @within(com.qiren.biz.annotation.DataSource) 类上有@DataSource注解,就将类中的方法拦截下来
     */
    @Pointcut("@annotation(com.cabbage.MultipleDataSources.annotation.DataSource) ||@within(com.cabbage.MultipleDataSources.annotation.DataSource)")
    public void pc() {
    }

    @Around("pc()")
    public Object around(ProceedingJoinPoint pjp) {
        //获取方法上的有效注解
        DataSource dataSource = getDataSource(pjp);
        if (dataSource != null) {
            //获取注解里数据源名称
            String value = dataSource.value();
            DynamicDataSourceContextHolder.setDateSourceType(value);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        return null;
    }

    private DataSource getDataSource(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        //查找方法上的注解
        DataSource a = AnnotationUtils.findAnnotation(methodSignature.getMethod(), DataSource.class);
        if (a != null) {
            //说明方法上有注解
            return a;
        }
        return AnnotationUtils.findAnnotation(methodSignature.getDeclaringType(), DataSource.class);
    }
}
