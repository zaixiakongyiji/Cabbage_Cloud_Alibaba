package com.cabbage.dynamicRoute.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "filter_type")
@Data
@TableName(value = "filter_type")
public class FilterType {
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_NAME_KEY_ONE = "name_key_one";
    public static final String COL_NAME_KEY_TWO = "name_key_two";
    public static final String COL_NAME_KEY_THREE = "name_key_three";
    public static final String COL_NAME_KEY_FOUR = "name_key_four";
    public static final String COL_NAME_KEY_FIVE = "name_key_five";
    public static final String COL_NAME_KEY_SIX = "name_key_six";
    public static final String COL_NAME_KEY_SEVEN = "name_key_seven";
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;
    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * key1
     */
    @TableField(value = "name_key_one")
    @ApiModelProperty(value = "key1")
    private String nameKeyOne;
    /**
     * key2
     */
    @TableField(value = "name_key_two")
    @ApiModelProperty(value = "key2")
    private String nameKeyTwo;
    /**
     * key3
     */
    @TableField(value = "name_key_three")
    @ApiModelProperty(value = "key3")
    private String nameKeyThree;
    /**
     * key4
     */
    @TableField(value = "name_key_four")
    @ApiModelProperty(value = "key4")
    private String nameKeyFour;
    /**
     * key5
     */
    @TableField(value = "name_key_five")
    @ApiModelProperty(value = "key5")
    private String nameKeyFive;
    /**
     * key6
     */
    @TableField(value = "name_key_six")
    @ApiModelProperty(value = "key6")
    private String nameKeySix;
    /**
     * key7
     */
    @TableField(value = "name_key_seven")
    @ApiModelProperty(value = "key7")
    private String nameKeySeven;
}