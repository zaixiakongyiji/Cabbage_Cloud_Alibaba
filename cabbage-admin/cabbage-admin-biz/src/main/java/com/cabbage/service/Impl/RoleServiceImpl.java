package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.mapper.RoleMapper;
import com.cabbage.dubbo.RoleService;
import com.cabbage.entity.Role;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@DubboService
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<Role> getListByUserId(Long id) {
        return this.baseMapper.getListByUserId(id);
    }
}
