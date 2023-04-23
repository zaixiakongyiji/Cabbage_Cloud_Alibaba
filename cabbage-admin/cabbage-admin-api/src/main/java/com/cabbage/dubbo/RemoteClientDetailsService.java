package com.cabbage.dubbo;

import com.cabbage.entity.SysOauthClientDetails;

public interface RemoteClientDetailsService {

    SysOauthClientDetails getClientDetailsById(String clientId);
}
