package com.cabbage.config;

import com.cabbage.handler.Handler;
import com.cabbage.handler.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class FormLoginConfig extends AbstractHttpConfigurer<FormLoginConfig, HttpSecurity> {
    @Override
    public void init(HttpSecurity hp) throws Exception {
        hp.formLogin(formLogin -> {
                    formLogin.loginPage("/token/login");
                    formLogin.loginProcessingUrl("/token/form");
                    formLogin.failureHandler(new Handler());

                }).logout() // SSO登出成功处理
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl()).deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and().csrf().disable();
    }


}
