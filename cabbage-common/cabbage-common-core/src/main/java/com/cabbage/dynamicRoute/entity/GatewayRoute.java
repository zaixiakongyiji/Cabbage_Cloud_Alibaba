package com.cabbage.dynamicRoute.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "gateway_route")
@Data
@TableName(value = "gateway_route")
public class GatewayRoute {
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_URI = "uri";
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名字")
    private String name;
    @TableField(value = "uri")
    @ApiModelProperty(value = "uri")
    private String uri;
}