package com.cabbage.dubbo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cabbage.entity.Permission;

import java.util.List;

public interface PermissionSevice extends IService<Permission> {
    List<Permission> getList(List<Long> rolesIds);
}
