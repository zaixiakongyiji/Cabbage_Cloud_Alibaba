package com.cabbage.core.domain.entity.unionkey;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoleKey implements Serializable {
    private static final long serialVersionUID = 5633412144183654743L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}
