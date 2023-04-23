package com.cabbage.config;

import com.cabbage.constants.SecurityConstants;
import com.cabbage.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private final OAuth2AuthorizationService authorizationService;

    @Autowired
    Handler handler;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Order(0)
    public SecurityFilterChain oAuth2AuthorizationFilterChain(HttpSecurity hp) throws Exception {
        OAuth2AuthorizationServerConfigurer oA2ASC = new OAuth2AuthorizationServerConfigurer();
        hp.apply(oA2ASC.tokenEndpoint(tokenEndpoint -> {
                    tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
                            .accessTokenResponseHandler(handler)
                            .errorResponseHandler(handler);
                }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
                        oAuth2ClientAuthenticationConfigurer.errorResponseHandler(handler))
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint// 授权码端点个性化confirm页面
                        .consentPage(SecurityConstants.AUTHORIZATION_CODE_MODE_URL)));
        DefaultSecurityFilterChain securityFilterChain = hp.authorizeHttpRequests(authorizeRequests -> {
                    // 自定义接口、端点暴露
                    authorizeRequests.requestMatchers("/login/**", "/actuator/**", "/css/**", "/error").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .apply(oA2ASC.authorizationService(authorizationService)// redis存储token的实现
                        .authorizationServerSettings(AuthorizationServerSettings.builder().issuer("https://tool.renqipd.top").build()))
                .and()
                .apply(new FormLoginConfig())
                .and()
                .build();
        hp.authenticationProvider(new DaoAuthenticationProvider());
        return securityFilterChain;
    }

    public AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }


}
