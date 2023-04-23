package com.cabbage.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.entity.SysLog;
import com.cabbage.mapper.SysLogMapper;
import com.cabbage.dubbo.SysLogService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
//    @Override
//    public void insert(SysLog sysLog) {
//        this.baseMapper.insert(sysLog);
//    }
}
