package com.cabbage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabbage.core.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
