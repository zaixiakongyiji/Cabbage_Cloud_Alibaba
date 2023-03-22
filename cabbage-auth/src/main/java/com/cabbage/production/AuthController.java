package com.cabbage.production;

import com.cabbage.core.domain.dto.Result;
import com.cabbage.core.domain.model.LoginBody;
import com.cabbage.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api("公共")
@RestController
public class AuthController {

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    @ApiOperation("公共登錄")
    public Result<String> login(@RequestBody LoginBody loginBody, HttpServletRequest request) {
        String jwt = loginService.login(loginBody, request);
        return Result.ok(jwt);
    }

    @ApiOperation("aa")
    @GetMapping("/aa")
    public String a() {
        String a = passwordEncoder.encode("123456");
        return a;
    }


}

