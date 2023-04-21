package com.cabbage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cabbage.core.service.PermissionSevice;
import com.cabbage.core.service.RoleService;
import com.cabbage.core.service.UserService;
import com.cabbage.core.domain.entity.Permission;
import com.cabbage.core.domain.entity.Role;
import com.cabbage.core.domain.entity.User;
import com.cabbage.core.domain.model.UserResult;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionSevice permissionSevice;

    @Override
    public UserResult loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        List<Role> roles = roleService.getListByUserId(user.getId());
        List<Long> rolesIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = permissionSevice.getList(rolesIds);
        UserResult u = UserResult.creat(user, roles, permissions);
        return u;
    }
}
