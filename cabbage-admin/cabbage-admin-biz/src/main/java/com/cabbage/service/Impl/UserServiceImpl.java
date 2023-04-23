package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.mapper.UserMapper;
import com.cabbage.dubbo.UserService;
import com.cabbage.entity.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
