package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.dubbo.GatewayPredicateService;
import com.cabbage.entity.route.GatewayPredicate;
import com.cabbage.mapper.GatewayPredicateMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class GatewayPredicateServiceImpl extends ServiceImpl<GatewayPredicateMapper, GatewayPredicate> implements GatewayPredicateService {

}
