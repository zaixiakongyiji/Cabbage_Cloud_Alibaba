package com.cabbage.service;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cabbage.core.domain.dto.RedisKey;
import com.cabbage.core.domain.model.LoginBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
//    String login(LoginBody loginBody, HttpServletRequest request);


    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    TokenService tokenService;
    @Autowired
    RedisKey redisKey;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${verify_code.status}")
    private Boolean verifyStatus;

//    @Override
    public String login(LoginBody loginBody, HttpServletRequest request) {
        if (StringUtils.isBlank(loginBody.getUsername()) || StringUtils.isBlank(loginBody.getPassword())) {
            throw new RuntimeException("用户名或密码为空!");
        }
        if (verifyStatus) {
            verificationCode(loginBody.getCode(), loginBody.getUuid());
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword()));
        Assert.notNull(authentication, "用户名或密码错误");
        String jwt = tokenService.createJWT(authentication, loginBody.getRememberMe());
//        Map<String, Object> map = new HashMap<>();
//        map.put("token",jwt);
        return jwt;
    }

    private void verificationCode(String code, String uuid) {
        String redisCode = stringRedisTemplate.opsForValue().get(redisKey.forBiz("captchaKey", uuid));
        Assert.notNull(redisCode, "验证码过期，请刷新");
        Assert.isTrue(code.equalsIgnoreCase(redisCode), "验证码错误");
    }


}
