package com.cabbage.config;

import com.cabbage.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig {

    @Resource
    UserDetailsService userDetailsService;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;


    @Bean
    JwtAuthenticationFilter authFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler).and()
                .logout().logoutSuccessHandler(logoutSuccessHandler).and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/swagger-ui.html*").anonymous()
                        .requestMatchers("/swagger-resources*").anonymous()
                        .requestMatchers("/doc.html**").anonymous()
                        .requestMatchers("/swagger/*").anonymous()
                        .requestMatchers("/v3/api-docs*").anonymous()
                        .requestMatchers("/static/*").anonymous()

                        .requestMatchers("/webjars/**").anonymous()
                        .requestMatchers("/druid/").anonymous()
                        .requestMatchers("/captcha*").anonymous()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)

                .userDetailsService(userDetailsService)
        ;

        return http.build();
    }

    /**
     * 配置跨源访问(CORS)
     *
     * @return
     */
    @Bean
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
