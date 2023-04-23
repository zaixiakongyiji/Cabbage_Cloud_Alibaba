package com.cabbage.dubbo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cabbage.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getListByUserId(Long id);
}
