package com.cabbage.core.domain.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "LoginDTO对象", description = "登录")
public class LoginBody {

    @ApiModelProperty("登录账号 手机 openId")
//    @NotNull("用户登录账号不能空")
    private String username;
    @ApiModelProperty("密码 ")
//    @NotNull("用户密码不能空")
    private String password;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("生成验证码的uuid")
    private String uuid;

    @ApiModelProperty("记住我")
    private Boolean rememberMe;


}
