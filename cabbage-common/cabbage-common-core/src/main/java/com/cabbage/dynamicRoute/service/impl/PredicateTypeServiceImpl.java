package com.cabbage.dynamicRoute.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.dynamicRoute.entity.PredicateType;
import com.cabbage.dynamicRoute.mapper.PredicateTypeMapper;
import com.cabbage.dynamicRoute.service.PredicateTypeService;
import org.springframework.stereotype.Service;

@Service
public class PredicateTypeServiceImpl extends ServiceImpl<PredicateTypeMapper, PredicateType> implements PredicateTypeService {
}
