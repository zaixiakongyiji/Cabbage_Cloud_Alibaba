package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.entity.SysOauthClientDetails;
import com.cabbage.mapper.SysOauthClientDetailsMapper;
import com.cabbage.dubbo.SysOauthClientDetailsService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {
    @Override
    public SysOauthClientDetails getClientDetailsById(String clientId) {
        return this.baseMapper.selectById(clientId);
    }
}
