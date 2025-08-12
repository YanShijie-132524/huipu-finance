package com.qst.finance.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.springframework.util.StringUtils;

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
//        // 直接用前端的 current/size
//        Page<SysUser> page = new Page<>(dto.getCurrent(), dto.getSize());
//
//        LambdaQueryWrapper<SysUser> qw = Wrappers.lambdaQuery(SysUser.class)
//                .and(StringUtils.hasText(dto.getKeyword()), w -> w
//                        .like(SysUser::getUserName, dto.getKeyword())
//                        .or().like(SysUser::getNickName, dto.getKeyword()))
//                .orderByDesc(SysUser::getCreateTime);
//
//        IPage<SysUser> p = sysUserMapper.selectPage(page, qw);
//
//        // 映射到 VO，并塞回 Page<UserListItemVO>
//        Page<UserListItemVO> voPage = new Page<>(p.getCurrent(), p.getSize(), p.getTotal());
//        List<UserListItemVO> records = p.getRecords().stream().map(this::toListItem).toList();
//        voPage.setRecords(records);
        return null;
    }

    private UserListItemVO toListItem(SysUser u){
        UserListItemVO vo = new UserListItemVO();
        vo.setId(u.getUserId());
        vo.setAvatar(u.getAvatar());
        vo.setCreateBy(u.getCreateBy());
        vo.setCreateTime(u.getCreateTime());
        vo.setUpdateBy(u.getUpdateBy());
        vo.setUpdateTime(u.getUpdateTime());
        vo.setStatus(mapStatus(u.getStatus(), u.getDelFlag())); // 1|2|3|4
        vo.setUserName(u.getUserName());
        vo.setUserGender(u.getSex());
        vo.setNickName(u.getNickName());
        vo.setUserPhone(u.getPhonenumber());
        vo.setUserEmail(u.getEmail());

        // 如果列表里也要显示该用户角色，可选：查询一次（注意 N+1，可批量优化）
        // vo.setUserRoles(roleMapper.selectRoleKeysByUserId(u.getUserId()));
        vo.setUserRoles(Collections.emptyList());
        return vo;
    }

    private String mapStatus(String status, String delFlag){
        if ("2".equals(delFlag)) return "4"; // 注销
        if ("0".equals(status)) return "1";  // 在线（示例）
        if ("1".equals(status)) return "2";  // 离线（示例）
        return "3";                          // 异常（兜底）
    }

}
