package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.dubbo.GatewayFilterService;
import com.cabbage.entity.route.GatewayFilter;
import com.cabbage.mapper.GatewayFilterMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class GatewayFilterServiceImpl extends ServiceImpl<GatewayFilterMapper, GatewayFilter> implements GatewayFilterService {

}
