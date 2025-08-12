package com.qst.finance.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qst.finance.controller.user.dto.request.LoginParamDto;
import com.qst.finance.controller.user.dto.request.UserPageQueryDTO;
import com.qst.finance.controller.user.dto.response.LoginDto;
import com.qst.finance.controller.user.vo.UserInfoVO;
import com.qst.finance.controller.user.vo.UserListItemVO;
import com.qst.finance.entity.SysUser;
import com.qst.finance.mapper.SysRoleMapper;
import com.qst.finance.mapper.SysUserMapper;
import com.qst.finance.security.jwt.JwtUtil;
import com.qst.finance.security.model.LoginUser;
import com.qst.finance.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Date 2025/08/09 18:09
 * @Created by YanShijie
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final SysUserMapper sysUserMapper;

    private final SysRoleMapper sysRoleMapper;

    @Override
    public LoginDto login(LoginParamDto loginDto) {

        // 1) 封装未认证请求
        UsernamePasswordAuthenticationToken authReq =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginDto.username(), loginDto.password()
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
        com.qst.finance.controller.user.dto.response.LoginDto dto = new com.qst.finance.controller.user.dto.response.LoginDto();
        dto.setToken(accessToken);
        dto.setRefreshToken(refreshToken);
        return dto;
    }

    @Override
    public UserInfoVO getUserInfo() {
        // 从Security上下文中获取当前认证用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        SysUser user = loginUser.getUser();
        // 1) 从角色表查 role_key 列表
        List<String> roles = sysRoleMapper.selectRoleKeysByUserId(user.getUserId());
        if (roles == null) roles = Collections.emptyList();

        // 2) buttons：通常来自权限点表；暂用 role_key 衍生或空列表
        //    你接入菜单权限后把这里替换掉
        List<String> buttons = new ArrayList<>();
        // 示例：根据角色简单给几个按钮
        if (roles.contains("admin")) {
            buttons = List.of("user:add","user:edit","user:delete","user:resetPwd");
        }

        return UserInfoVO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhonenumber())
                .roles(roles)
                .buttons(buttons)
                .build();
    }

    @Override
    public Page<UserListItemVO> page(UserPageQueryDTO dto) {
        return sysUserMapper.selectUserPage(dto);
    }

}
