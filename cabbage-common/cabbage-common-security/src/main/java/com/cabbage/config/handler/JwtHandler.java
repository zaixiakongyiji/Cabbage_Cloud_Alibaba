package com.cabbage.config.handler;

import com.cabbage.core.domain.dto.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class JwtHandler {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> Result.ero(401, "访问异常", "请先登录", response);
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, accessDeniedException) -> Result.ero(403, "身份验证异常", "权限不足", response);
    }

}
