package com.cabbage.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisPrefix {
    @Value("${data.redis.prefix:def-prefix}")
    private String prefix;

    public String initialization(String modules, String parameter) {
        return prefix + ":" + modules + ":" + parameter;
    }
}
