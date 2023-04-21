package com.cabbage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabbage.core.domain.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> getList(@Param("rolesIds") List<Long> rolesIds);
}
