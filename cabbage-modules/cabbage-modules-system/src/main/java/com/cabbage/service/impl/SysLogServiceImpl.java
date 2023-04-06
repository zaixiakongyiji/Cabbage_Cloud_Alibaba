package com.cabbage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.domain.SysLog;
import com.cabbage.mapper.SysLogMapper;
import com.cabbage.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
