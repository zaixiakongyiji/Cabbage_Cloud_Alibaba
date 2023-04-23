package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.entity.route.PredicateType;
import com.cabbage.mapper.PredicateTypeMapper;
import com.cabbage.dubbo.PredicateTypeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class PredicateTypeServiceImpl extends ServiceImpl<PredicateTypeMapper, PredicateType> implements PredicateTypeService {
}
