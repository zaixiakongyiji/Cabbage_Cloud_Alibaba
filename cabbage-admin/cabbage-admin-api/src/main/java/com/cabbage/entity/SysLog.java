package com.cabbage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "系统日志", description = "系统日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "请求url")
    private String requestUrl;

    @ApiModelProperty(value = "请求方式，POST、GET")
    private String requestMethod;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "接口状态（0成功 1失败）")
    private Integer status;

    @ApiModelProperty(value = "错误信息")
    private String errorText;

    @ApiModelProperty(value = "耗时,毫秒")
    private String takeUpTime;

    @TableField("CREATED_BY")
    @ApiModelProperty(value = "创建人")
    private Integer createdBy;

    @TableField("CREATED_BY_NAME")
    @ApiModelProperty(value = "创建人名")
    private String createdByName;


    @TableField("CREATED_TIME")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;
}
