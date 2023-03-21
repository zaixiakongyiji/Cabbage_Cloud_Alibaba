package com.cabbage.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cabbage.core.domain.model.UserResult;
import com.cabbage.service.CustomUserDetailsService;
import com.cabbage.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = tokenService.getJwtFromRequest(request);
        if (token != null) {
            log.info("有令牌 走Jwt");
            String username = tokenService.getUsernameFromJWT(token);
            if (StringUtils.checkValNotNull(username)) {
                UserResult userResult = customUserDetailsService.loadUserByUsername(username);
                //刷新令牌
                tokenService.verifyToken(userResult);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userResult, null, userResult.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("用户'{}'的Token校验成功", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.info("没令牌 走登錄");
        }
        chain.doFilter(request, response);
    }
}
