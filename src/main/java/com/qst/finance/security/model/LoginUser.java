package com.qst.finance.security.model;

import com.qst.finance.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Classname LoginUser
 * @Description 自定义用户登录信息模型，实现 Spring Security 提供的 UserDetails 接口。
 * UserDetails 用于封装认证所需的用户信息，供 Security 框架在认证与授权时调用。
 * @Date 2025/08/09 18:33
 * @Created by YanShijie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class LoginUser implements UserDetails {

    private SysUser user;

    /**
     * 获取当前用户的权限集合（角色、权限字符串等）。
     * 通常从数据库查询后封装成 GrantedAuthority 对象列表返回。
     * 此处返回空列表，表示无任何权限。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 获取当前用户的密码（用于认证时进行密码校验）。
     * 实际使用时应返回加密后的密码（BCrypt 等）。
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取当前用户的用户名（登录凭证，比如账号、邮箱、手机号等）。
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否未过期。
     * true 表示账户有效，false 表示账户已过期（过期则无法登录）。
     * 此处调用了父接口默认实现（始终返回 true）。
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未被锁定。
     * true 表示未锁定，false 表示已锁定（锁定则无法登录）。
     * 此处调用了父接口默认实现（始终返回 true）。
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证（密码）是否未过期。
     * true 表示密码有效，false 表示密码过期（过期则需要修改密码）。
     * 此处调用了父接口默认实现（始终返回 true）。
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否启用。
     * true 表示已启用，false 表示已禁用（禁用则无法登录）。
     * 此处调用了父接口默认实现（始终返回 true）。
     */
    @Override
    public boolean isEnabled() {
        return user.getStatus().equals("0");
    }
}