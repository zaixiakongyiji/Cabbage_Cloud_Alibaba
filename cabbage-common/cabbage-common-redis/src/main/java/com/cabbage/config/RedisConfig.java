package com.cabbage.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }

//    @Bean
//    public RedisKeyExpiredListenerConfig keyExpiredListener() {
//        return new RedisKeyExpiredListenerConfig(this.redisMessageListenerContainer());
//    }

    /**
     * 防止redis存储字符时带前缀导致找不到
     *
     * @param 'redisConnectionFactory'
     * @return
     */
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        template.setHashKeySerializer(serializer);
        template.setKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.setValueSerializer(serializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> limit = new DefaultRedisScript<>();
        limit.setResultType(Long.class);
        limit.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        return limit;
    }

    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;

    }
}
