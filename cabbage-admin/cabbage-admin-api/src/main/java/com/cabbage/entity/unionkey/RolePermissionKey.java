package com.cabbage.entity.unionkey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "RolePermissionKey",description = "角色权限关联")
public class RolePermissionKey implements Serializable {
    private static final long serialVersionUID = 6850974328279713855L;


    @ApiModelProperty("角色id")
    private Long roleId;


    @ApiModelProperty("权限id")
    private Long permissionId;
}
