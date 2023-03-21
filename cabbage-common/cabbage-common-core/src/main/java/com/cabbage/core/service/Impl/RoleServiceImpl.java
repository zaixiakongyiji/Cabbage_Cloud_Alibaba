package com.cabbage.core.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.core.mapper.RoleMapper;
import com.cabbage.core.service.RoleService;
import com.cabbage.core.domain.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<Role> getListByUserId(Long id) {
        return this.baseMapper.getListByUserId(id);
    }
}
