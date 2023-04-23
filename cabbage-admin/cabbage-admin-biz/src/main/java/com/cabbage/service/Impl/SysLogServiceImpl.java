package com.cabbage.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.GatwayRoute.api.SystemService;
import com.cabbage.GatwayRoute.entity.SysLog;
import com.cabbage.mapper.SysLogMapper;
import com.cabbage.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService, SystemService {
    @Override
    public void insert(SysLog sysLog) {
        this.baseMapper.insert(sysLog);
    }
}
