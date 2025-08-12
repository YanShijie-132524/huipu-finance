package com.qst.finance.security.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qst.finance.entity.SysUser;
import com.qst.finance.mapper.SysUserMapper;
import com.qst.finance.security.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Classname UserDetailsServiceImpl
 * @Description
 * @Date 2025/08/09 20:04
 * @Created by YanShijie
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("不存在此用户");
        }
        // 判断密码是否正确
        // TODO

        // 返回UserDetails对象
        return new LoginUser(user);
    }
}
