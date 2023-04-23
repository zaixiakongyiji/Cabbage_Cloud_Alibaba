package com.cabbage.service;

import cn.hutool.core.util.StrUtil;
import com.cabbage.entity.Permission;
import com.cabbage.entity.Role;
import com.cabbage.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResult implements UserDetails {

    /**
     * 索引(唯一, 必填)
     */
    private Long id;

    private String username;
    /**
     * 加密密码
     */
    private String password;

//    /**
//     * 数据权限
//     */
//    private Set<String> dataPermission;

//    /**
//     * 额外信息
//     */
//    private Map<String, Object> ext;

    private String nickName;
    /**
     * 电话号码
     */
    private String phone;


    /**
     * 性别
     */
    private Integer sex;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status;

    private List<String> roles;

    /**
     * 密码
     *
     * @param password 密码
     * @return this
     */
    public UserResult setPassword(String password) {
        this.password = password;
        if (password.length() > 4) {
            this.password = password.substring(0, 4).concat("****").concat(password.substring(4));
        } else {
            this.password = password.concat("****");
        }
        return this;
    }

//    /**
//     * 转化
//     *
//     * @param target 目标类型
//     * @param <T>    类型
//     * @return 结果
//     */
//    public <T> T toBean(Class<T> target) {
//        T newInstance = null;
//        try {
//            newInstance = target.newInstance();
//        } catch (Exception ignored) {
//        }
//
//        if (null == newInstance) {
//            return null;
//        }
//
//        Map<String, Object> beanMap = this.ext;
//        T finalNewInstance = newInstance;
//        ReflectionUtils.doWithFields(target, field -> {
//            field.setAccessible(true);
//            Object o = beanMap.get(field.getName());
//            if (null == o) {
//                return;
//            }
//
//            try {
//                field.set(finalNewInstance, o);
//            } catch (Exception ignore) {
//            }
//        });
//
//        return finalNewInstance;
//    }

    private Collection<? extends GrantedAuthority> authorities;

    public static UserResult creat(User user, List<Role> roles, List<Permission> permissions){
        List<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());
        List<GrantedAuthority> authorities = permissions.stream().filter(permission -> StrUtil.isNotBlank(permission.getPermission())).map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
        return new UserResult(user.getId(),user.getUsername(), user.getPassword(), user.getNickname(), user.getPhone(),  user.getSex(),user.getStatus(), roleNames, authorities);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.status, 1);
    }
}
