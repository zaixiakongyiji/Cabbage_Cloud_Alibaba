package com.cabbage.entity.route;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "gateway_predicate")
@Data
@TableName(value = "gateway_predicate")
public class GatewayPredicate {
    public static final String COL_ID = "id";
    public static final String COL_RID = "rId";
    public static final String COL_NAME = "name";
    public static final String COL_PARAMS_TYPE = "params_type";
    public static final String COL_PARAMS_ONE = "params_one";
    public static final String COL_PARAMS_TWO = "params_two";
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
     * 断言名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "断言名称")
    private String name;
    /**
     * 参数1
     */
    @TableField(value = "params_one")
    @ApiModelProperty(value = "参数1")
    private String paramsOne;
    /**
     * 参数2
     */
    @TableField(value = "params_two")
    @ApiModelProperty(value = "参数2")
    private String paramsTwo;
}