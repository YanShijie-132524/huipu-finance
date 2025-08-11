package com.qst.finance.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.finance.controller.user.dto.request.LoginParam;
import com.qst.finance.controller.user.dto.response.LoginDto;
import com.qst.finance.entity.SysUser;
import com.qst.finance.mapper.UserMapper;
import com.qst.finance.security.jwt.JwtUtil;
import com.qst.finance.security.model.LoginUser;
import com.qst.finance.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Date 2025/08/09 18:09
 * @Created by YanShijie
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Override
    public LoginDto login(LoginParam loginParam) {

        // 1) 封装未认证请求
        UsernamePasswordAuthenticationToken authReq =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginParam.username(), loginParam.password()
                );

        // 2) 认证
        Authentication authResult = authenticationManager.authenticate(authReq);

        // 3) 认证通过，获取主体
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        Long userId = loginUser.getUser().getUserId();

        // 4) 生成 token（JWT 只存 userId + 轻量 claims）
        Map<String, Object> baseClaims = Map.of("uid", userId);

        // access：60分钟
        String accessToken = jwtUtil.generateAccessToken(userId, baseClaims, Duration.ofHours(1).toMillis());
        // refresh：使用全局配置（也可自定义更长）
        String refreshToken = jwtUtil.generateRefreshToken(userId, baseClaims, null);

        // 5) 返回
        LoginDto dto = new LoginDto();
        dto.setToken(accessToken);
        dto.setRefreshToken(refreshToken);
        return dto;
    }

}
