package com.cabbage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cabbage.core.domain.entity.Permission;

import java.util.List;

public interface PermissionSevice extends IService<Permission> {
    List<Permission> getList(List<Long> rolesIds);
}
