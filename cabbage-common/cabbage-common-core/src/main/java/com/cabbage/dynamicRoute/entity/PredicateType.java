package com.cabbage.dynamicRoute.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "predicate_type")
@Data
@TableName(value = "predicate_type")
public class PredicateType {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名字")
    private String name;
    @TableField(value = "name_key_one")
    @ApiModelProperty(value = "键值1")
    private String nameKeyOne;
    @TableField(value = "name_key_two")
    @ApiModelProperty(value = "键值2")
    private String nameKeyTwo;
}