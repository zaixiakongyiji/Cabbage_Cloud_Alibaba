package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.dubbo.FilterTypeService;
import com.cabbage.entity.route.FilterType;
import com.cabbage.mapper.FilterTypeMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class FilterTypeServiceImpl extends ServiceImpl<FilterTypeMapper, FilterType> implements FilterTypeService {

}
