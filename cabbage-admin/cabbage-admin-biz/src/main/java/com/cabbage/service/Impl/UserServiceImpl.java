package com.cabbage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabbage.mapper.UserMapper;
import com.cabbage.service.UserService;
import com.cabbage.core.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
