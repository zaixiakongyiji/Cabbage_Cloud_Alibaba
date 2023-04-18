package com.cabbage.dynamicRoute.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "gateway_filter")
@Data
@TableName(value = "gateway_filter")
public class GatewayFilter {
    public static final String COL_PARAM_TYPE = "param_type";
    public static final String COL_ID = "id";
    public static final String COL_RID = "rId";
    public static final String COL_NAME = "name";
    public static final String COL_PARAM_ONE = "param_one";
    public static final String COL_PARAM_TWO = "param_two";
    public static final String COL_PARAM_THREE = "param_three";
    public static final String COL_PARAM_FOUR = "param_four";
    public static final String COL_PARAM_FIVE = "param_five";
    public static final String COL_PARAM_SIX = "param_six";
    public static final String COL_PARAM_SEVEN = "param_seven";
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;
    /**
     * 路由id
     */
    @TableField(value = "rId")
    @ApiModelProperty(value = "路由id")
    private Integer rid;
    /**
     * 过滤器名字
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "过滤器名字")
    private String name;
    /**
     * 参数1
     */
    @TableField(value = "param_one")
    @ApiModelProperty(value = "参数1")
    private String paramOne;
    /**
     * 参数2
     */
    @TableField(value = "param_two")
    @ApiModelProperty(value = "参数2")
    private String paramTwo;
    /**
     * 参数3
     */
    @TableField(value = "param_three")
    @ApiModelProperty(value = "参数3")
    private String paramThree;
    /**
     * 参数4
     */
    @TableField(value = "param_four")
    @ApiModelProperty(value = "参数4")
    private String paramFour;
    /**
     * 参数5
     */
    @TableField(value = "param_five")
    @ApiModelProperty(value = "参数5")
    private String paramFive;
    /**
     * 参数6
     */
    @TableField(value = "param_six")
    @ApiModelProperty(value = "参数6")
    private String paramSix;
    /**
     * 参数7
     */
    @TableField(value = "param_seven")
    @ApiModelProperty(value = "参数7")
    private String paramSeven;
}