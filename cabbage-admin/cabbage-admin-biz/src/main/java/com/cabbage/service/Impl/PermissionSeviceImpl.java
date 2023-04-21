package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.mapper.PermissionMapper;
import com.cabbage.service.PermissionSevice;
import com.cabbage.core.domain.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionSeviceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionSevice {
    @Override
    public List<Permission> getList(List<Long> rolesIds) {
        return this.baseMapper.getList(rolesIds);
    }
}
