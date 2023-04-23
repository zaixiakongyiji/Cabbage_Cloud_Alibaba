package com.cabbage.production;

import com.cabbage.dto.Result;
import com.cabbage.dubbo.RemoteClientDetailsService;
import com.cabbage.entity.SysOauthClientDetails;
import com.cabbage.model.LoginBody;
import com.cabbage.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;


@Slf4j
@Api(tags = "公共")
@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @DubboReference
    RemoteClientDetailsService clientDetailsService;


    @GetMapping("/doLogin")
    @ApiOperation("公共登錄")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("ftl/login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @PostMapping("/confirm_authorization")
    @ApiOperation("授权码")
    public ModelAndView confirmAuthorization(Principal principal, ModelAndView modelAndView,
                                                     @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                                     @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                                     @RequestParam(OAuth2ParameterNames.STATE) String state) {
        try {
            SysOauthClientDetails clientDetails = clientDetailsService.getClientDetailsById(clientId);
            Set<String> authorizedScopes = StringUtils.commaDelimitedListToSet(clientDetails.getScope());
            modelAndView.addObject("clientId", clientId);
            modelAndView.addObject("state", state);
            modelAndView.addObject("scopeList", authorizedScopes);
            modelAndView.addObject("principalName", principal.getName());
            modelAndView.setViewName("ftl/confirm");
            return modelAndView;
        } catch (Exception e) {
            log.error("clientId 不合法");
            throw new OAuth2AuthenticationException("clientId 不合法");
        }
    }

    @ApiOperation("aa")
    @GetMapping("/aa")
    public String a() {
        String a = passwordEncoder.encode("123456");
        return a;
    }


}

//登陆 生成token