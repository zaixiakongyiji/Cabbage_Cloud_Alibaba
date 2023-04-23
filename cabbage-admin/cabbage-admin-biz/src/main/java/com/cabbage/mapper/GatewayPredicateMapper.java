package com.cabbage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabbage.entity.route.GatewayPredicate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GatewayPredicateMapper extends BaseMapper<GatewayPredicate> {
}