package com.cabbage.config.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cabbage.service.TokenService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * 自定义退出处理类 返回成功
 *
 * @author tienchin
 */
@Configuration
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String username = tokenService.getUsernameFromRequest(request);
        if (StringUtils.checkValNotNull(username)) {
            log.info("用户'{}'正在登出", username);
            if (tokenService.invalidateJWT(request)) {
                log.info("用户'{}'登出成功", username);
            } else {
                log.info("用户'{}'登出失败", username);
            }
        }
    }
}
