package com.cabbage.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("sec_role")
@ApiModel(value = "Role",description = "角色")
public class Role {


    @ApiModelProperty("主键id")
    private Long id;


    @ApiModelProperty("角色名")
    private String name;


    @ApiModelProperty("描述")
    private String description;


    @ApiModelProperty("创建时间")
    private Long createTime;


    @ApiModelProperty("更新时间")
    private Long updateTime;
}
