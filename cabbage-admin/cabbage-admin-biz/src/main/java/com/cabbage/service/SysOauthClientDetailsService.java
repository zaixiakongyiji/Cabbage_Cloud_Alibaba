package com.cabbage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cabbage.entity.SysOauthClientDetails;

public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

    SysOauthClientDetails getClientDetailsById(String clientId);

}
