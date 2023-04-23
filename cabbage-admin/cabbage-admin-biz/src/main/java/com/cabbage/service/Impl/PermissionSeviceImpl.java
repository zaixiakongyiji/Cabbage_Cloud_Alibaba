package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.entity.Permission;
import com.cabbage.mapper.PermissionMapper;
import com.cabbage.dubbo.PermissionSevice;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@DubboService
@Service
public class PermissionSeviceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionSevice {
    @Override
    public List<Permission> getList(List<Long> rolesIds) {
        return this.baseMapper.getList(rolesIds);
    }
}
