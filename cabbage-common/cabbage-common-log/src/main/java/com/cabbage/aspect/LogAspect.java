package com.cabbage.aspect;

import com.cabbage.annotation.Log;
import com.cabbage.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAspect {


    @Resource
    SystemService service;



    /**
     * 获取请求参数
     *
     * @param pjp       切入点
     * @param signature 方法签名
     */
    public static String getRequestParam(ProceedingJoinPoint pjp, MethodSignature signature) {
        Object[] args = pjp.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap.toString();
    }

    @Pointcut("@annotation(com.cabbage.annotation.Log)")
    public void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        SysLog sysLog = new SysLog();
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes
                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Log logs = methodSignature.getMethod().getAnnotation(Log.class);
            if (logs != null && !"".equals(logs.value())) {
                sysLog.setModuleName(logs.value());
            } else {
                Api api = pjp.getTarget().getClass().getAnnotation(Api.class);
                ApiOperation aon = methodSignature.getMethod().getAnnotation(ApiOperation.class);
                sysLog.setModuleName(api.tags()[0] + '-' + aon.value());
            }
            sysLog.setIp(IpUtils.getIpAddr(request));
            sysLog.setClassName(pjp.getTarget().getClass().getName());
            sysLog.setRequestUrl(request.getRequestURI());
            sysLog.setRequestMethod(request.getMethod());
            sysLog.setRequestParam(getRequestParam(pjp, methodSignature));
            sysLog.setStatus(0);
            long startTime = System.currentTimeMillis();
            result = pjp.proceed();
            long endTime = System.currentTimeMillis();
            sysLog.setTakeUpTime(String.format("%s", endTime - startTime));
            /*if (result != null) {
                sysLog.setResultText(String.valueOf(result));
            }*/
        } catch (Exception e) {
            result = e.getMessage();
            sysLog.setStatus(1);
            if (result != null) {
                sysLog.setErrorText(String.valueOf(result));
            }
            return pjp.proceed();
        } finally {
//            存数据库
            service.insert(sysLog);
        }

        return result;
    }

}
