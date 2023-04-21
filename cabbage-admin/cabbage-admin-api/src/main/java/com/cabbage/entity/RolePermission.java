package com.cabbage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cabbage.entity.unionkey.RolePermissionKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("sec_role_permission")
@ApiModel(value = "RolePermission",description = "角色权限关联")
public class RolePermission {

    @ApiModelProperty("主键Id")
    private RolePermissionKey id;
}
