package com.cabbage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cabbage.dubbo.PermissionSevice;
import com.cabbage.dubbo.RoleService;
import com.cabbage.dubbo.UserService;
import com.cabbage.entity.Permission;
import com.cabbage.entity.Role;
import com.cabbage.entity.User;
import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @DubboReference
    UserService userService;

    @DubboReference
    RoleService roleService;

    @DubboReference
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
