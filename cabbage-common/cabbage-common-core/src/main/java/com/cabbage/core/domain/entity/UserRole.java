package com.cabbage.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cabbage.core.domain.entity.unionkey.UserRoleKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sec_user_role")
@ApiModel(value = "UserRole",description = "用户角色关联")
public class UserRole {

    @ApiModelProperty("主键Id")
    private UserRoleKey id;
}
