package com.cabbage.controller;

import cn.hutool.core.lang.UUID;
//import com.cabbage.core.domain.dto.Captcha;
import com.cabbage.core.domain.dto.CaptchaConfig;
import com.cabbage.core.domain.dto.RedisPrefix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/captcha")
@Api(tags = "验证码")
@Slf4j
public class CaptchaController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CaptchaConfig captcha;
    @Autowired
    private RedisPrefix redisPrefix;

    @GetMapping("/{uuid}")
    @ApiOperation("验证码")
    public void getCaptcha(@PathVariable("uuid") String uuid, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(CaptchaConfig.RESPONSE_KEY_UUID, uuid);
        captcha.generateCaptcha(session, response);
        Object attribute = session.getAttribute(CaptchaConfig.SESSION_KEY);
        String captchaKey = redisPrefix.initialization("captchaKey", uuid);
        redisTemplate.opsForValue().set(captchaKey, attribute.toString(), 10, TimeUnit.MINUTES);
        log.info("缓存 {} 登录码是 {}", captchaKey, attribute);
    }

    @GetMapping
    @ApiOperation("验证码-从响应头中获取uuid（CAPTCHA_UUID）")
    public void getCaptchaNoUuid(HttpServletRequest request, HttpServletResponse response) {
        String uuid = UUID.randomUUID(true).toString(true);
        HttpSession session = request.getSession();
        session.setAttribute(CaptchaConfig.RESPONSE_KEY_UUID, uuid);
        captcha.generateCaptcha(session, response);
        Object attribute = session.getAttribute(CaptchaConfig.SESSION_KEY);
        String captchaKey = redisPrefix.initialization("captchaKey", uuid);
        redisTemplate.opsForValue().set(captchaKey, attribute.toString(), 10, TimeUnit.MINUTES);
        log.info("缓存 {} 登录码是 {}", captchaKey, attribute);
    }
}
