package com.cabbage.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("sec_permission")
@ApiModel(value = "Permission",description = "权限")
public class Permission {

    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    private String name;

    @ApiModelProperty("路由地址/接口地址")
    private String url;

    @ApiModelProperty("权限类型，页面-1，按钮-2")
    private Integer type;

    @ApiModelProperty("权限表达式")
    private String permission;

    @ApiModelProperty("后端接口访问方式")
    private String method;


    @ApiModelProperty("排序")
    private Integer sort;


    @ApiModelProperty("父级id")
    private Long parentId;
}
