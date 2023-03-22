package com.cabbage.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@TableName("sec_user")
@ApiModel(value = "User",description = "用户")
public class User {

    @ApiModelProperty("主键Id")
    private Long id;


    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机")
    private String phone;


    @ApiModelProperty("邮箱")
    private String email;


    @ApiModelProperty("生日")
    private Long birthday;


    @ApiModelProperty("性别，男-1，女-2")
    private Integer sex;


    @ApiModelProperty("状态，启用-1，禁用-0")
    private Integer status;


    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
