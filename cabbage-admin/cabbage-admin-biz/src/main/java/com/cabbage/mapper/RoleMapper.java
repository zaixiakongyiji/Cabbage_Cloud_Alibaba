package com.cabbage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabbage.core.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getListByUserId(Long id);
}
