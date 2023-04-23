package com.cabbage.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cabbage.constants.SecurityConstants;
import com.cabbage.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;


@Configuration
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    TokenService tokenService;

    /**
     * 退出处理
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (response == null) {
            return;
        }
        String username = tokenService.getUsernameFromRequest(request);
        String redirectUrl = request.getParameter(SecurityConstants.REDIRECT_URL);

        if (StringUtils.checkValNotNull(username)) {
            log.info("用户'{}'正在登出", username);
            if (tokenService.invalidateJWT(request)) {
                log.info("用户'{}'登出成功", username);
                if (StrUtil.isNotBlank(redirectUrl)) {
                    response.sendRedirect(redirectUrl);
                } else if (StrUtil.isNotBlank(request.getHeader(HttpHeaders.REFERER))) {
                    // 默认跳转referer 地址
                    String referer = request.getHeader(HttpHeaders.REFERER);
                    response.sendRedirect(referer);
                }
            } else {
                log.info("用户'{}'登出失败", username);
            }
        }
    }
}
