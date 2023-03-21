package com.cabbage.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cabbage.core.domain.entity.unionkey.RolePermissionKey;
import lombok.Data;


@Data
@TableName("sec_role_permission")
public class RolePermission {
    /**
     * 主键
     */
    private RolePermissionKey id;
}
