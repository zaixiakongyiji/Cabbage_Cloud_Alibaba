package com.cabbage.dynamicRoute.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cabbage.dynamicRoute.entity.FilterType;
import com.cabbage.dynamicRoute.mapper.FilterTypeMapper;
import com.cabbage.dynamicRoute.service.FilterTypeService;
import org.springframework.stereotype.Service;

@Service
public class FilterTypeServiceImpl extends ServiceImpl<FilterTypeMapper, FilterType> implements FilterTypeService {

}
