package com.cabbage.config.handler;//package org.example.config.handler;
//
//import org.example.core.domain.dto.ResultDTO;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Configuration
//public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        ResultDTO.ero(403, "身份验证异常","权限不足",response);
//
//    }
//}
