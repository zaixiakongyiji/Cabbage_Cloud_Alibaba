package com.cabbage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabbage.entity.route.GatewayRoute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GatewayRouteMapper extends BaseMapper<GatewayRoute> {
}