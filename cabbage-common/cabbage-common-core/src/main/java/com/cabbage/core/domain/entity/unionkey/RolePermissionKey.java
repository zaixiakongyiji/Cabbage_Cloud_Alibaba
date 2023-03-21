package com.cabbage.core.domain.entity.unionkey;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermissionKey implements Serializable {
    private static final long serialVersionUID = 6850974328279713855L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;
}
