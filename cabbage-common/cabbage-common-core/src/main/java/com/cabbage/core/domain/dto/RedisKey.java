package com.cabbage.core.domain.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yzj@1239694214@qq.com
 */
@Component
public class RedisKey {
    @Value("${redis.prefix:def-prefix}")
    private String prefix;

    public String forBiz(String biz, String keyFlag) {
        return prefix + ":" + biz + ":" + keyFlag;
    }
}
