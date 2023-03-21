package com.cabbage.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cabbage.core.domain.entity.unionkey.UserRoleKey;
import lombok.Data;

@Data
@TableName("sec_user_role")
public class UserRole {
    /**
     * 主键
     */
    private UserRoleKey id;
}
